package com.iths.jh.RecipeApplication.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.services.RecipeService;

@RestController
@RequestMapping("recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@GetMapping
	public List<Recipe> getAllRecipes() {
		List<Recipe> recipes = recipeService.findAll();
		recipes.forEach((recipe) -> System.out.println("User: " + recipe.getUser().fullName()));
		return recipes;
	}

	@GetMapping
	@RequestMapping("{id}")
	public Optional<Recipe> getRecipeById(@PathVariable Long id) {
		return recipeService.findById(id);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Optional<Recipe> createRecipe(@RequestBody Recipe newRecipe) {
		System.out.println("POST");
		System.out.println(newRecipe);
		try {
			
			Optional<Recipe> serviceResponse = recipeService.create(newRecipe);
			if (serviceResponse.isPresent()) {
				return serviceResponse;
			}
			return Optional.empty();
		} catch (RuntimeException e) {

			return Optional.empty();
		}

	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Optional<Recipe> updateRecipe(@RequestBody Recipe newRecipe) {

		try {

			Optional<Recipe> serviceResponse = recipeService.updateById(newRecipe);
			if (serviceResponse.isPresent()) {
				return serviceResponse;
			}
			return Optional.empty();
		} catch (RuntimeException e) {

			return Optional.empty();
		}
	}

	@DeleteMapping("{id}")
	public Optional<Recipe> deleteRecipe(@PathVariable(value = "id") Long recipeId) {
		try {

			Optional<Recipe> serviceResponse = recipeService.deleteById(recipeId);
			if (serviceResponse.isPresent()) {
				return serviceResponse;
			}
			return Optional.empty();
		} catch (RuntimeException e) {

			return Optional.empty();
		}
	}

}
