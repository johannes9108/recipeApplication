package com.iths.jh.RecipeApplication.rest;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iths.jh.RecipeApplication.domain.FoodCategory;
import com.iths.jh.RecipeApplication.domain.Ingredient;
import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.domain.User;
import com.iths.jh.RecipeApplication.repositories.FoodCategoryRepository;
import com.iths.jh.RecipeApplication.repositories.IngredientRepository;
import com.iths.jh.RecipeApplication.repositories.RecipeRepository;
import com.iths.jh.RecipeApplication.repositories.UserRepository;

@RestController
public class HomeController {
	
		@RequestMapping("/")
	    public String index(){
	        System.out.println("Home Page");
	        return "Welcome";
	    }

}
