package com.iths.jh.RecipeApplication.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.iths.jh.RecipeApplication.domain.FoodCategory;
import com.iths.jh.RecipeApplication.domain.Ingredient;
import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.domain.User;
import com.iths.jh.RecipeApplication.repositories.FoodCategoryRepository;
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
public class FoodCategoryService implements ServiceInterface<FoodCategory>{

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Override
    public ServiceResponse<FoodCategory> findById(Long id) {
        ServiceResponse<FoodCategory> response = new ServiceResponse<FoodCategory>();

        try {
            FoodCategory recipe = foodCategoryRepository.findById(id).orElseThrow(NoSuchElementException::new);
            response.setResponseObject(recipe);
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<FoodCategory> findAll(int page, int size) {
        ServiceResponse<FoodCategory> response = new ServiceResponse<FoodCategory>();
        try {
            System.out.println("Return all FoodCategorys");
            Pageable pageable = PageRequest.of(page>=1?page-1:0, Math.max(size, 1));
            Page<FoodCategory> listOfFoodCategories = foodCategoryRepository.findAll(pageable);
            response.setResponseObjects(listOfFoodCategories.getContent());
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<FoodCategory> findAll(SearchParams searchParams, int page, int size) {
        ServiceResponse<FoodCategory> response = new ServiceResponse<>();
        try {
            System.out.println("Return all food categories");
            Pageable pageable = PageRequest.of(page>=1?page-1:0, Math.max(size, 1));
            Page<FoodCategory> pagedFoodCategories = foodCategoryRepository.findAllFiltered(searchParams, pageable);
            response.setResponseObjects(pagedFoodCategories.getContent());
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<FoodCategory> create(FoodCategory newFoodCategory) {
        ServiceResponse<FoodCategory> response = new ServiceResponse<>();
        try {
            // Prevents an accidental update by discarding the id
            newFoodCategory.setId(null);
            newFoodCategory.setName(newFoodCategory.getName().toUpperCase());

            FoodCategory recipe = foodCategoryRepository.save(newFoodCategory);
            response.setResponseObject(recipe);
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
            log.error(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<FoodCategory> update(FoodCategory newFoodCategory) {
        ServiceResponse<FoodCategory> response = new ServiceResponse<>();
        try {
            newFoodCategory.setName(newFoodCategory.getName().toUpperCase());
            FoodCategory ingredient = foodCategoryRepository.save(newFoodCategory);
            System.out.println("Updated ingredient with id: " + ingredient.getId());
            response.setResponseObject(ingredient);
        } catch (Exception e) {
            response.addErrorMessage(e.getLocalizedMessage());
            log.error(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ServiceResponse<FoodCategory> patch(Long id, JsonPatch patch) {
        ServiceResponse<FoodCategory> response = new ServiceResponse<>();
        try {
            FoodCategory foodCategory = foodCategoryRepository.findById(id).orElseThrow(NoSuchElementException::new);
            FoodCategory foodCategoryPatched = applyPatchToFoodCategory(patch, foodCategory);
            log.info("Updated foodCategory: " + foodCategoryPatched.toString());
            System.out.println("Updated foodCategory: " + foodCategoryPatched.toString());
            ServiceResponse<FoodCategory> op = update(foodCategoryPatched);
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

    private FoodCategory applyPatchToFoodCategory(JsonPatch patch, FoodCategory targetFoodCategory)throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        JsonNode patched = patch.apply(objectMapper.convertValue(targetFoodCategory, JsonNode.class));
        return objectMapper.treeToValue(patched, FoodCategory.class);
    }

    @Override
    public ServiceResponse<FoodCategory> deleteById(Long id) {
        ServiceResponse<FoodCategory> response = new ServiceResponse<>();
        Optional<FoodCategory> foodCategory = foodCategoryRepository.findById(id);
        if (foodCategory.isPresent()) {
            System.out.println("FoodCategory with id to be deleted: " + foodCategory.get().getId());
            foodCategoryRepository.deleteById(id);
            response.setResponseObject(foodCategory.get());
        } else {
            response.addErrorMessage(ServiceErrorMessages.RECIPE.couldNotFind(id));
        }
        return response;
    }
}
