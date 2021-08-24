package com.iths.jh.RecipeApplication.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.iths.jh.RecipeApplication.domain.Ingredient;
import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.domain.User;
import com.iths.jh.RecipeApplication.repositories.IngredientRepository;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import com.iths.jh.RecipeApplication.utilities.ServiceErrorMessages;
import com.iths.jh.RecipeApplication.utilities.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class IngredientService implements ServiceInterface<Ingredient> {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public ServiceResponse<Ingredient> findById(Long id) {
        ServiceResponse<Ingredient> response = new ServiceResponse<Ingredient>();

        try {
            Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(NoSuchElementException::new);
            response.setResponseObject(ingredient);
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<Ingredient> findAll(int page, int size) {
        ServiceResponse<Ingredient> response = new ServiceResponse<Ingredient>();
        try {
            System.out.println("Return all Ingredients");
            Pageable pageable = PageRequest.of(page>=1?page-1:0, Math.max(size, 1));
            Page<Ingredient> listOfIngredients = ingredientRepository.findAll(pageable);
            response.setResponseObjects(listOfIngredients.getContent());
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<Ingredient> findAll(SearchParams searchParams, int page, int size) {
        ServiceResponse<Ingredient> response = new ServiceResponse<>();
//        try {
//            System.out.println("Return all Recipes");
//            Pageable pageable = PageRequest.of(page>=1?page-1:0, Math.max(size, 1));
//            Page<Recipe> pagedRecipes = ingredientRepository.(searchParams, pageable);
//            response.setResponseObjects(pagedRecipes.getContent());
//        } catch (Exception e) {
//            response.addErrorMessage(e.getLocalizedMessage());
//        }
        return response;
    }

    @Override
    public ServiceResponse<Ingredient> create(Ingredient newIngredient) {
        ServiceResponse<Ingredient> response = new ServiceResponse<>();
        try {
            // Prevents an accidental update by discarding the id
            newIngredient.setId(null);
            Ingredient recipe = ingredientRepository.save(newIngredient);
            response.setResponseObject(recipe);
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
            log.error(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<Ingredient> update(Ingredient newIngredient) {
        ServiceResponse<Ingredient> response = new ServiceResponse<>();
        try {
            Ingredient ingredient = ingredientRepository.save(newIngredient);
            System.out.println("Updated ingredient with id: " + ingredient.getId());
            response.setResponseObject(ingredient);
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
            log.error(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<Ingredient> patch(Long id, JsonPatch patch) {
        return null;
    }

    @Override
    public ServiceResponse<Ingredient> deleteById(Long id) {
        ServiceResponse<Ingredient> response = new ServiceResponse<>();
//        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
//        if (ingredient.isPresent()) {
//            System.out.println("Ingredient with id to be deleted: " + ingredient.get().getId());
//            ingredientRepository.deleteById(id);
//            response.setResponseObject(ingredient.get());
//        } else {
//            response.addErrorMessage(ServiceErrorMessages.RECIPE.couldNotFind(id));
//        }
        return response;


    }
}
