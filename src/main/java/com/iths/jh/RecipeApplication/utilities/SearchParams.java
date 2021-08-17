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

    private List<FoodCategory> categoryList;
    private List<Ingredient> ingredientList;
    private Set<String> words;

}
