package com.iths.jh.RecipeApplication.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
//@SqlResultSetMapping(
//        name = "Recipe",
//        classes = @ConstructorResult(
//                targetClass = BookWithAuthorNames.class,
//                columns = { @ColumnResult(name = "id", type = Long.class), 
//                            @ColumnResult(name = "title"), 
//                            @ColumnResult(name = "price"), 
//                            @ColumnResult(name = "authorName")}))
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private Long views;
	private LocalDate publishedDate;

	private String instructions;
//	, joinColumns = {
//			@JoinColumn(nullable = false, name = "recipe_id", referencedColumnName = "id"),
//			@JoinColumn(name = "ingredient", referencedColumnName = "id") }

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "Quantities",
	joinColumns = {
	})
	@MapKeyColumn(name = "Ingredient_FK",nullable = false)
	private Map<Long, Quantity> quantityPerIngredient;

	// TODO CASCADING IS ONLY FOR DEVELOPMENT
	//TODO Try to get CASCADE.PERSIST to work? Detached entity passed to persist Currently
//	@JoinTable(name = "R_FC",joinColumns = @JoinColumn(name="recipeID"),
//			inverseJoinColumns  = @JoinColumn(name="foodCategoryId"))
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<FoodCategory> foodCategories;

	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public Recipe(String title, Long views, LocalDate publishedDate, String instructions) {
		super();
		this.title = title;
		this.views = views;
		this.publishedDate = publishedDate;
		this.instructions = instructions;
		foodCategories = new HashSet<>();
		quantityPerIngredient = new HashMap<>();
	}

	@Override
	public String toString() {
		String userString = user != null ? user.fullName() : "No user";
		String ingredientsString = quantityPerIngredient.size() > 0 ? formattedIngredients() : "No ingredients";
		return "Recipe [id=" + id + ", title=" + title + ", views=" + views + ", publishedDate=" + publishedDate
				+ ", instructions=" + instructions + ", ingredients=" + ingredientsString + ", foodCategories="
				+ foodCategories + ", user=" + userString + "]";
	}

	private String formattedIngredients() {
		StringBuilder builder = new StringBuilder("");
		int counter = 1;
		for (Entry<Long, Quantity> es : quantityPerIngredient.entrySet()) {
			builder.append("|" + counter++ + ":" + es.getValue().getQuantity() + " " + es.getValue().getUnit() + " "
					+ es.getKey());
		}
		builder.append("|");
		return builder.toString();
	}
	
	public boolean addFoodCategory(FoodCategory newFoodCategory) {
		return foodCategories.add(newFoodCategory);
	}
	public boolean removeFoodCategory(long id) {
		return foodCategories.removeIf(category->category.getId()==id);
	}

}
