package com.iths.jh.RecipeApplication.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
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

	private HashMap<Long, Recipe> recipes = new HashMap<Long, Recipe>();
	{
		Recipe newRecipe = new Recipe("Pasta Carbonara", 10L, LocalDate.now(), "Stek Fläsk|Koka pasta");
		newRecipe.setId(1L);
		recipes.put(newRecipe.getId(), newRecipe);
		newRecipe = new Recipe("Raggmunkar", 10L, LocalDate.now(), "Stek Fläsk|Koka pasta");
		newRecipe.setId(2L);
		recipes.put(newRecipe.getId(), newRecipe);
		newRecipe = new Recipe("Plättar", 10L, LocalDate.now(), "Stek Fläsk|Koka pasta");
		newRecipe.setId(3L);
		recipes.put(newRecipe.getId(), newRecipe);
		newRecipe = new Recipe("Entrecóte", 10L, LocalDate.now(), "Stek Fläsk|Koka pasta");
		newRecipe.setId(4L);
		recipes.put(newRecipe.getId(), newRecipe);

	}

	public void printSomething() {
		System.out.println("Testing");
	}

	@Override
	public List<Recipe> findAll() {
		System.out.println("Return all Recipes");
		return recipeRepository.findAllFetched();
	}

	@Override
	public Optional<Recipe> findById(Long id) {
		return recipeRepository.findByIdFetched(id);
	}

	@Override
	@Transactional(value = TxType.REQUIRES_NEW)
	public Optional<Recipe> deleteById(Long id) {
		try {
			
			Optional<Recipe> recipe = recipeRepository.findByIdFetched(id);
			if(recipe.isPresent()) {
				
				System.out.println("User to be deleted" + recipe.get().getUser().getFirstName());
				
				System.out.println("Recipe with id to be deleted: " + recipe.get().getId());
				recipeRepository.deleteById(recipe.get().getId());
			}
			else {
				
			}
			return recipe;
		} catch (NoSuchElementException e) {
			log.error(e.getLocalizedMessage());
			throw new RuntimeException("Problem with deleting service");
		}

	}
	

	@Override
	public Optional<Recipe> updateById(Recipe newData) {
		try {
			Optional<Recipe> recipeToBeUpdated = recipeRepository.findByIdFetched(newData.getId());
			System.out.println(recipeToBeUpdated);
			if(recipeToBeUpdated.isPresent()) {
				Recipe recipeOld = recipeToBeUpdated.get();
//				if(newData.getFoodCategories()==null)
//					newData.setFoodCategories(new HashSet<FoodCategory>());
				recipeOld = newData;
				Recipe recipe = recipeRepository.saveAndFlush(recipeOld);
				System.out.println("Updated recipe with id: " + recipe.getId());
				return Optional.of(recipe);
			}
			else {
				System.out.println("Couldnt find product to be updated");
				return Optional.empty();
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new RuntimeException("Problem with updating service");
		}
	}

	@Override
	public Optional<Recipe> create(Recipe newRecipe) {
		try {
			//TODO PERMANENT USER FOR DEVELOPMENT
			User user = userRepository.findById(1L).get();
			newRecipe.setUser(user);
			Recipe recipe = recipeRepository.saveAndFlush(newRecipe);
			System.out.println("Recipe with id: " + recipe.getId());
			return Optional.of(recipe);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new RuntimeException("Problem with creating service");
		}
	}

}
