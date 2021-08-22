package com.iths.jh.RecipeApplication.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class FoodCategory {

    @Id
    private String name;

    public enum FoodCategoryPredefined {
        VEGAN, DAIRY, MEAT, FISH
    }

    public FoodCategory(String name) {
        this.name = name;

    }
    public FoodCategory(FoodCategoryPredefined name) {
        this.name = name.toString();
    }


    @Override
    public String toString() {
        return "Food Category:" + name;
    }


}
