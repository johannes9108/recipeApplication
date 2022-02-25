package com.iths.jh.RecipeApplication.domain;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {


	enum IngredientPredefined{
		MILK, WHEAT, CREAM, TOMATOES
	}
	public Ingredient(String name) {
		this.name = name;
		
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	@Override
	public String toString() {
		return "Ingredient:" + name;
	}
	
	
}
