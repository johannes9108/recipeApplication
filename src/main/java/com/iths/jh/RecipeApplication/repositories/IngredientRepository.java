package com.iths.jh.RecipeApplication.repositories;

import com.iths.jh.RecipeApplication.repositories.customQueries.IngredientRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iths.jh.RecipeApplication.domain.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientRepositoryCustom {



}
