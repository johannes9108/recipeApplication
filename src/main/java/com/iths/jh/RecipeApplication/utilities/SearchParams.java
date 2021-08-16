package com.iths.jh.RecipeApplication.utilities;

import com.iths.jh.RecipeApplication.domain.FoodCategory;
import com.iths.jh.RecipeApplication.domain.Ingredient;
import lombok.Data;

import java.util.List;

@Data
public class SearchParams {

    private List<FoodCategory> categoryList;
    private List<Ingredient> ingredientList;

    private String words;

}
