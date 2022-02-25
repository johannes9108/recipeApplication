package com.iths.jh.RecipeApplication.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.fge.jsonpatch.JsonPatch;
import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.services.ServiceInterface;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import com.iths.jh.RecipeApplication.utilities.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iths.jh.RecipeApplication.domain.FoodCategory;
import com.iths.jh.RecipeApplication.domain.Ingredient;
import com.iths.jh.RecipeApplication.repositories.FoodCategoryRepository;
import com.iths.jh.RecipeApplication.repositories.IngredientRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("miscellaneous")
public class MiscellaneousController {

	@Autowired
	private ServiceInterface<Ingredient> ingredientService;

	@Autowired
	private ServiceInterface<FoodCategory> foodCategoryService;

	Logger logger = LoggerFactory.getLogger(MiscellaneousController.class);


	/*
	INGREDIENT SECTION
	 */

	@GetMapping(value = "/ingredients/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
		ServiceResponse<Ingredient> response = ingredientService.findById(id);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObject());
		}
		else{
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "ingredients")
	public ResponseEntity<List<Ingredient>> getAllIngredients(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
		ServiceResponse<Ingredient> response = ingredientService.findAll(page,size);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObjects());
		}
		else{
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}

	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/ingredients/search")
	public ResponseEntity<List<Ingredient>> getAllIngredients(@RequestBody SearchParams searchParams, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
		logger.warn(searchParams.toString());
		System.out.println("page: " + page + ", size: " + size );
		ServiceResponse<Ingredient> response = ingredientService.findAll(searchParams, page, size);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObjects());
		}
		else{
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "ingredients")
	public ResponseEntity<Ingredient> createIngredient(@RequestBody @Valid Ingredient newIngredient) {
		logger.debug("Inside POST Ingredient");

		ServiceResponse<Ingredient> response = ingredientService.create(newIngredient);
		if (response.isSucessful()) {
			logger.info(response.toString());
			return ResponseEntity.ok(response.getResponseObject());
		}
		else{
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "ingredients")
	public ResponseEntity<Ingredient> updateIngredient(@RequestBody Ingredient newIngredient) {

		ServiceResponse<Ingredient> response = ingredientService.update(newIngredient);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObject());
		}
		else{
			return ResponseEntity.badRequest().build();
		}
	}

	@PatchMapping(path = "ingredients/{id}", consumes = "application/json-patch+json",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Ingredient> patchIngredient(@PathVariable Long id, @RequestBody JsonPatch patch) {
		ServiceResponse<Ingredient> response = ingredientService.patch(id,patch);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObject());
		}
		else{
			return ResponseEntity.badRequest().build();
		}
	}



	@DeleteMapping(value = "/ingredients/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Long id) {
		ServiceResponse<Ingredient> response = ingredientService.deleteById(id);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObject());
		}
		else{
			return ResponseEntity.badRequest().build();
		}
	}


	/*
	FOODCATEGORY SECTION

	 */

	@GetMapping(value = "/foodcategories/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<FoodCategory> getFoodCategoryById(@PathVariable Long id) {
		ServiceResponse<FoodCategory> response = foodCategoryService.findById(id);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObject());
		}
		else{
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/foodcategories")
	public ResponseEntity<List<FoodCategory>> getAllFoodCategories(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
		ServiceResponse<FoodCategory> response = foodCategoryService.findAll(page,size);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObjects());
		}
		else{
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}

	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/foodcategories/search")
	public ResponseEntity<List<FoodCategory>> getAllFoodCategories(@RequestBody @Valid SearchParams searchParams, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
		logger.warn(searchParams.toString());
		System.out.println("page: " + page + ", size: " + size );
		ServiceResponse<FoodCategory> response = foodCategoryService.findAll(searchParams, page, size);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObjects());
		}
		else{
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}



	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/foodcategories")
	public ResponseEntity<FoodCategory> createFoodCategory(@RequestBody @Valid FoodCategory newFoodCategory) {
		logger.debug("Inside POST FoodCategory");

		ServiceResponse<FoodCategory> response = foodCategoryService.create(newFoodCategory);
		if (response.isSucessful()) {
			logger.info(response.toString());
			return ResponseEntity.ok(response.getResponseObject());
		}
		else{
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/foodcategories")
	public ResponseEntity<FoodCategory> updateFoodCategory(@RequestBody FoodCategory newFoodCategory) {

		ServiceResponse<FoodCategory> response = foodCategoryService.update(newFoodCategory);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObject());
		}
		else{
			return ResponseEntity.badRequest().build();
		}
	}

	@PatchMapping(path = "/foodcategories/{id}", consumes = "application/json-patch+json",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<FoodCategory> patchFoodCategory(@PathVariable Long id, @RequestBody JsonPatch patch) {
		ServiceResponse<FoodCategory> response = foodCategoryService.patch(id,patch);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObject());
		}
		else{
			return ResponseEntity.badRequest().build();
		}
	}



	@DeleteMapping(value = "/foodcategories/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<FoodCategory> deleteFoodCategory(@PathVariable Long id) {
		ServiceResponse<FoodCategory> response = foodCategoryService.deleteById(id);
		if (response.isSucessful()) {
			return ResponseEntity.ok(response.getResponseObject());
		}
		else{
			return ResponseEntity.badRequest().build();
		}
	}



	
	
}
