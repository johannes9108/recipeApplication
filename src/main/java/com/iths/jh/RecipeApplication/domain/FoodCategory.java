package com.iths.jh.RecipeApplication.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class FoodCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "foodCategories")
    @JsonIgnore
    private Set<Recipe> recipes;

    @PreRemove
    private void removeRecipesFromFoodCategory() {
        for (Recipe r : recipes) {
            r.getFoodCategories().remove(this);
        }
    }

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
