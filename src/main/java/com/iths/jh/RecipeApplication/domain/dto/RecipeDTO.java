package com.iths.jh.RecipeApplication.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RecipeDTO{

    private Long id;
    private String title;
    private LocalDate publishedDate;
    private String instructions;
    private int cookingTime;
    private int preparingTime;
    private Long views;

}
