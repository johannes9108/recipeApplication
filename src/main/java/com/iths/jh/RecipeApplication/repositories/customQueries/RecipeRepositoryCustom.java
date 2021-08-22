package com.iths.jh.RecipeApplication.repositories.customQueries;

import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeRepositoryCustom {

    Page<Recipe> findAllFetched(SearchParams searchParams, Pageable pageable);
}
