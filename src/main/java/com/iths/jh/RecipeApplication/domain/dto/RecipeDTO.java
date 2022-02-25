package com.iths.jh.RecipeApplication.domain.dto;

import com.iths.jh.RecipeApplication.domain.FoodCategory;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class RecipeDTO{

    private Long id;
    private String title;
    private LocalDate publishedDate;
    private String instructions;
    private int cookingTime;
    private int preparingTime;
    private Long views;
    private Set<FoodCategory> foodCategories;
    private List<String> ingredientEntries;

}
