package com.iths.jh.RecipeApplication.utilities;

import com.iths.jh.RecipeApplication.domain.FoodCategory;
import com.iths.jh.RecipeApplication.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParams {

    // Higheest priority
    private Set<String> words;

    // Secondary priority
    private List<FoodCategory> categoryList;
    private Set<String> ingredientList;

}
