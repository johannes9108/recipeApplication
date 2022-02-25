package com.iths.jh.RecipeApplication.repositories;

import com.iths.jh.RecipeApplication.repositories.customQueries.FoodCategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iths.jh.RecipeApplication.domain.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long>, FoodCategoryRepositoryCustom {

}
