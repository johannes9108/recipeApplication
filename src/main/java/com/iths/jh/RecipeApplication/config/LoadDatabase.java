package com.iths.jh.RecipeApplication.config;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import com.iths.jh.RecipeApplication.domain.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.iths.jh.RecipeApplication.repositories.FoodCategoryRepository;
import com.iths.jh.RecipeApplication.repositories.IngredientRepository;
import com.iths.jh.RecipeApplication.repositories.RecipeRepository;
import com.iths.jh.RecipeApplication.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {
	
	
	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, RecipeRepository recipeRepository,
			IngredientRepository ingredientRepository, FoodCategoryRepository foodCategoryRepository) {
		return args ->{
			User user = new User("Johannes","Hedman",20,"jh","1234", LocalDate.now(),"mail@mail.com");
			User user2 = new User("Atif","Cheema",20,"ac","1234", LocalDate.now(),"mail@mail.com");
			User user3 = new User("Adam","Traore",20,"at","1234", LocalDate.now(),"mail@mail.com");
			User user4 = new User("Anders","Erkson",20,"ae","1234", LocalDate.now(),"mail@mail.com");
			Recipe recipe = new Recipe("Pasta Carbonara",0L, LocalDate.now(),"1. Blanda");
			Recipe recipe2 = new Recipe("Kött Carbonara",0L, LocalDate.now(),"1. Blanda");
			Recipe recipe3 = new Recipe("Fisk Carbonara",0L, LocalDate.now(),"1. Blanda");
			Recipe recipe4 = new Recipe("Manna Carbonara",0L, LocalDate.now(),"1. Blanda");


			log.info("----Load Database START----");

			Ingredient ingredient = new Ingredient("Bacon");
			ingredientRepository.save(ingredient);
			recipe.addIngredient("500g Äpplen");
			recipe2.addIngredient("3kg Vit Bröd");
			ingredient = new Ingredient("Apples");
//			ingredientRepository.save(ingredient);
			recipe2.addIngredient("500g Päron");
//			ingredient = new Ingredient("Mushrooms");
//			ingredientRepository.save(ingredient);
			recipe3.addIngredient("200g Nötkött");
			recipe4.addIngredient("1 Msk  Rapsolja");
			FoodCategory foodCategory = new FoodCategory(FoodCategory.FoodCategoryPredefined.MEAT);
			foodCategoryRepository.save(foodCategory);
			recipe.addFoodCategory(foodCategory);
			
			user.addRecipe(recipe);
			
			recipe3.addFoodCategory(foodCategory);
			foodCategory = new FoodCategory(FoodCategory.FoodCategoryPredefined.VEGAN);
			foodCategoryRepository.save(foodCategory);
			recipe3.addFoodCategory(foodCategory);
			user.addRecipe(recipe3);
			
			
			user2.addRecipe(recipe2);
			foodCategory = new FoodCategory(FoodCategory.FoodCategoryPredefined.MEAT);
			foodCategoryRepository.save(foodCategory);
			recipe2.addFoodCategory(foodCategory);
			foodCategory = new FoodCategory("SPICY");
			foodCategoryRepository.save(foodCategory);
			recipe4.addFoodCategory(foodCategory);
			
			user2.addRecipe(recipe4);
//			recipe4.getFoodCategories().add(foodCategory);
			
			
			
//			recipeRepository.save(recipe);
//			recipeRepository.save(recipe2);
//			recipeRepository.save(recipe3);
//			recipeRepository.save(recipe4);
//			HashSet<Recipe> set = new HashSet<Recipe>();
//			set.add(recipe);
//			user.setRecipes(set);
			
			
			userRepository.save(user);
			userRepository.save(user2);
			userRepository.save(user3);
			userRepository.save(user4);

			log.info("----Load Database START----");
			
//			recipeRepository.findAllFetched().forEach(System.out::println);;
//			System.out.println(r);
//			if(r.isPresent())
//			System.out.println(userRepository.findByIdFetched(1L));
				
			
		};
	}
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	

}
