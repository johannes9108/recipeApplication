package com.iths.jh.RecipeApplication.rest;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.services.RecipeService;

import javax.swing.text.html.Option;
import javax.validation.Valid;

@RestController
@RequestMapping("recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	Logger logger = LoggerFactory.getLogger(RecipeController.class);

	@GetMapping
	public List<Recipe> getAllRecipes(SearchParams searchParams) {

		try {
			List<Recipe> recipes = recipeService.findAll(searchParams);
			recipes.forEach((recipe) -> System.out.println("User: " + recipe.getUser().fullName()));
			return recipes;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());

			return null;
		}
	}

	@GetMapping
	@RequestMapping("{id}")
	public Optional<Recipe> getRecipeById(@PathVariable Long id) {
		return recipeService.findById(id);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Optional<Recipe> createRecipe(@RequestBody @Valid Recipe newRecipe) {
		try {

			logger.debug("Inside POST Recipe");

			Optional<Recipe> serviceResponse = recipeService.create(newRecipe);
			if (serviceResponse.isPresent()) {

			System.out.println(serviceResponse.get());
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

			Optional<Recipe> serviceResponse = recipeService.updateRecipe(newRecipe);
			if (serviceResponse.isPresent()) {
				return serviceResponse;
			}
			return Optional.empty();
		} catch (RuntimeException e) {

			return Optional.empty();
		}
	}

	@PatchMapping(path = "{id}", consumes = "application/json-patch+json")
	public Optional<Recipe> patchUpdateRecipe(@PathVariable Long id, @RequestBody JsonPatch patch) {
		try {
			Recipe recipe = recipeService.findById(id).orElseThrow(RuntimeException::new);
			Recipe recipePatched = applyPatchToRecipe(patch, recipe);
			logger.info("Updated recipe: " + recipePatched.toString());
			System.out.println("Updated recipe: " + recipePatched.toString());
			recipeService.updateRecipe(recipePatched);
			return Optional.of(recipePatched);
		} catch (JsonPatchException | JsonProcessingException | RuntimeException e) {
			return Optional.empty();
		}
	}

	private Recipe applyPatchToRecipe(
			JsonPatch patch, Recipe targetRecipe) throws JsonPatchException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode patched = patch.apply(objectMapper.convertValue(targetRecipe, JsonNode.class));
		return objectMapper.treeToValue(patched, Recipe.class);
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
