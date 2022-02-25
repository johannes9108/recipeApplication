package com.iths.jh.RecipeApplication.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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

import javax.validation.Valid;
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
            Pageable pageable = PageRequest.of(page >= 1 ? page - 1 : 0, Math.max(size, 1));
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
        try {
            log.error("" + searchParams);
            System.out.println("Filtered Ingredients");
            Pageable pageable = PageRequest.of(page >= 1 ? page - 1 : 0, Math.max(size, 1));
            Page<Ingredient> pagedIngredients = ingredientRepository.findAllFiltered(searchParams, pageable);
            response.setResponseObjects(pagedIngredients.getContent());
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<Ingredient> create(@Valid Ingredient newIngredient) {
        ServiceResponse<Ingredient> response = new ServiceResponse<>();
        try {
            // Prevents an accidental update by discarding the id
            newIngredient.setId(null);
            newIngredient.setName(newIngredient.getName().toUpperCase());
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
            newIngredient.setName(newIngredient.getName().toUpperCase());
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
        ServiceResponse<Ingredient> response = new ServiceResponse<>();
        try {
            Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(NoSuchElementException::new);
            Ingredient ingredientPatched = applyPatchToIngredient(patch, ingredient);
            log.info("Updated ingredient: " + ingredientPatched.toString());
            System.out.println("Updated ingredient: " + ingredientPatched.toString());
            ServiceResponse<Ingredient> op = update(ingredientPatched);
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

        private Ingredient applyPatchToIngredient(JsonPatch patch, Ingredient targetIngredient)throws JsonPatchException, JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            JsonNode patched = patch.apply(objectMapper.convertValue(targetIngredient, JsonNode.class));
            return objectMapper.treeToValue(patched, Ingredient.class);

    }

    @Override
    public ServiceResponse<Ingredient> deleteById(Long id) {
        ServiceResponse<Ingredient> response = new ServiceResponse<>();
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
        if (ingredient.isPresent()) {
            System.out.println("Ingredient with id to be deleted: " + ingredient.get().getId());
            ingredientRepository.deleteById(id);
            response.setResponseObject(ingredient.get());
        } else {
            response.addErrorMessage(ServiceErrorMessages.RECIPE.couldNotFind(id));
        }
        return response;


    }
}
