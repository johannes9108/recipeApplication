package com.iths.jh.RecipeApplication.rest;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iths.jh.RecipeApplication.domain.FoodCategory;
import com.iths.jh.RecipeApplication.domain.Ingredient;
import com.iths.jh.RecipeApplication.repositories.FoodCategoryRepository;
import com.iths.jh.RecipeApplication.repositories.IngredientRepository;

public class MiscellaneousController {

	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private FoodCategoryRepository foodCategoryRepository;
	
	@RequestMapping("ingredients")
	public ResponseEntity<Set<Ingredient>> getAllIngredient() {
		return new ResponseEntity<Set<Ingredient>>(new HashSet<Ingredient>(ingredientRepository.findAll()),HttpStatus.OK) ;
	}
	
	@RequestMapping("foodCategories")
	public ResponseEntity<Set<FoodCategory>> getAllFoodCategories() {
		return new ResponseEntity<Set<FoodCategory>>(new HashSet<FoodCategory>(foodCategoryRepository.findAll()),HttpStatus.OK) ;
	}
	
	
}
