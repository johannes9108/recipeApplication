package com.iths.jh.RecipeApplication.services;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import com.iths.jh.RecipeApplication.utilities.ServiceErrorMessages;
import com.iths.jh.RecipeApplication.utilities.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iths.jh.RecipeApplication.domain.FoodCategory;
import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.domain.User;
import com.iths.jh.RecipeApplication.repositories.RecipeRepository;
import com.iths.jh.RecipeApplication.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeService implements ServiceInterface<Recipe> {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    public void printSomething() {
        System.out.println("Testing");
    }

    @Override
    public ServiceResponse<Recipe> findById(Long id) {
        ServiceResponse<Recipe> response = new ServiceResponse<Recipe>();

        try {
            System.out.println("Return all Recipes");
            Recipe recipe = recipeRepository.findByIdFetched(id).orElseThrow(NoSuchElementException::new);
            response.setResponseObject(recipe);
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
        }
        return response;
    }
    @Override
    public ServiceResponse<Recipe> findAll(int page, int size) {
        ServiceResponse<Recipe> response = new ServiceResponse<Recipe>();
        try {
            System.out.println("Return all Recipes");
            Pageable pageable = PageRequest.of(page>=1?page-1:0, Math.max(size, 1));
            List<Recipe> listOfRecipes = recipeRepository.findAllFetched(pageable);
            response.setResponseObjects(listOfRecipes);
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<Recipe> findAll(SearchParams searchParams, int page, int size) {
        ServiceResponse<Recipe> response = new ServiceResponse<>();
        try {
            System.out.println("Return all Recipes");
            Pageable pageable = PageRequest.of(page>=1?page-1:0, Math.max(size, 1));
            Page<Recipe> pagedRecipes = recipeRepository.findAllFetched(searchParams, pageable);
            response.setResponseObjects(pagedRecipes.getContent());
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<Recipe> create(Recipe newRecipe) {
        ServiceResponse<Recipe> response = new ServiceResponse<>();
        try {
            // Prevents an accidental update by discarding the id
            newRecipe.setId(null);
            //TODO PERMANENT USER FOR DEVELOPMENT
            User user = userRepository.findById(1L).get();
            newRecipe.setUser(user);
            Recipe recipe = recipeRepository.save(newRecipe);
            response.setResponseObject(recipe);
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
            log.error(e.getLocalizedMessage());
        }
        return response;
    }


    @Override
    public ServiceResponse<Recipe> update(Recipe newData) {

        ServiceResponse<Recipe> response = new ServiceResponse<>();
        try {
            Recipe recipeToBeUpdated = recipeRepository.findByIdFetched(newData.getId()).orElseThrow(NoSuchElementException::new);
            recipeToBeUpdated = newData;
//				if(newData.getFoodCategories()==null)
//					newData.setFoodCategories(new HashSet<FoodCategory>());
            Recipe recipe = recipeRepository.save(recipeToBeUpdated);
            System.out.println("Updated recipe with id: " + recipe.getId());
            response.setResponseObject(recipe);
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
            log.error(e.getLocalizedMessage());
        }
        return response;
    }


    @Override
    public ServiceResponse<Recipe> patch(Long id, JsonPatch patch) {
        ServiceResponse<Recipe> response = new ServiceResponse<>();
        try {
            Recipe recipe = recipeRepository.findByIdFetched(id).orElseThrow(NoSuchElementException::new);
            Recipe recipePatched = applyPatchToRecipe(patch, recipe);
            log.info("Updated recipe: " + recipePatched.toString());
            System.out.println("Updated recipe: " + recipePatched.toString());
            ServiceResponse<Recipe> op = update(recipePatched);
            if (op.isSucessful()) {
                response.setResponseObject(op.getResponseObject());
            } else {
                response.addErrorMessage(ServiceErrorMessages.RECIPE.couldNotUpdate(id));
            }
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
        }
        return response;
    }

    private Recipe applyPatchToRecipe(
            JsonPatch patch, Recipe targetRecipe) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        JsonNode patched = patch.apply(objectMapper.convertValue(targetRecipe, JsonNode.class));
        return objectMapper.treeToValue(patched, Recipe.class);
    }

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public ServiceResponse<Recipe> deleteById(Long id) {

        ServiceResponse<Recipe> response = new ServiceResponse<>();
        Optional<Recipe> recipe = recipeRepository.findByIdFetched(id);
        if (recipe.isPresent()) {

            System.out.println("User to be deleted" + recipe.get().getUser().getFirstName());

            System.out.println("Recipe with id to be deleted: " + recipe.get().getId());
            recipeRepository.deleteById(id);
            response.setResponseObject(recipe.get());
        } else {
            response.addErrorMessage(ServiceErrorMessages.RECIPE.couldNotFind(id));
        }
        return response;

    }

}
