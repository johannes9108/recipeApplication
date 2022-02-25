package com.iths.jh.RecipeApplication.repositories.customQueries;

import com.iths.jh.RecipeApplication.domain.FoodCategory;
import com.iths.jh.RecipeApplication.domain.Ingredient;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodCategoryRepositoryCustom {
    Page<FoodCategory> findAllFiltered(SearchParams searchParams, Pageable pageable);
}
