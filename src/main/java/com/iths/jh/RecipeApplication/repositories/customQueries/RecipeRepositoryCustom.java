package com.iths.jh.RecipeApplication.repositories.customQueries;

import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.utilities.SearchParams;

import java.util.List;

public interface RecipeRepositoryCustom {

    List<Recipe> findAllFetched(SearchParams searchParams);
}
