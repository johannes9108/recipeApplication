package com.iths.jh.RecipeApplication.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

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
public class Recipe implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@PositiveOrZero(message = "Cooking Time mustn't be negative")
	@Value(value = "0")
	private int cookingTime;
	@PositiveOrZero( message = "Preparing Time mustn't be negative")
	@Value(value = "0")
	private int preparingTime;


	@NotBlank
	@Column(nullable = false)
	@Size(min = 3, message = "Title needs to be at least 3 characters long")
	private String title;

	@Column(nullable = false)
	@Value(value = "0")
	private Long views;
	@Column(nullable = false)
	@PastOrPresent
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate publishedDate;


	private String instructions;

	@ElementCollection
	@CollectionTable(name = "recipe_Ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
	private List<String> ingredientEntries;

//	, joinColumns = {
//			@JoinColumn(nullable = false, name = "recipe_id", referencedColumnName = "id"),
//			@JoinColumn(name = "ingredient", referencedColumnName = "id") }

//	@ElementCollection(fetch = FetchType.LAZY)
//	@CollectionTable(name = "Quantities",
//	joinColumns = {
//	})
//	@MapKeyColumn(name = "Ingredient_FK",nullable = false)
//	private Map<Long, Quantity> quantityPerIngredient;

	// TODO CASCADING IS ONLY FOR DEVELOPMENT
	//TODO Try to get CASCADE.PERSIST to work? Detached entity passed to persist Currently
//	@JoinTable(name = "R_FC",joinColumns = @JoinColumn(name="recipeID"),
//			inverseJoinColumns  = @JoinColumn(name="foodCategoryId"))
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<FoodCategory> foodCategories;


	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public Recipe(String title, Long views, LocalDate publishedDate, String instructions) {
		super();
		this.title = title;
		this.views = views;
		this.publishedDate = publishedDate;
		this.instructions = instructions;
		foodCategories = new HashSet<>();
		ingredientEntries = new LinkedList<>();
	}

	@Override
	public String toString() {
		String userString = user != null ? user.fullName() : "No user";
		String ingredientsString = ingredientEntries.size()>0? formattedIngredients() : "No ingredients";
		return "Recipe [id=" + id + ", title=" + title + ", views=" + views + ", publishedDate=" + publishedDate
				+ ", instructions=" + instructions + ", ingredients=" + ingredientsString + ", foodCategories="
				+ foodCategories + ", user=" + userString + "]";
	}

	private String formattedIngredients() {
		StringBuilder builder = new StringBuilder("");
		int counter = 1;
		for (String ingredient : ingredientEntries) {
			builder.append(counter++ + ":" + ingredient);
		}
		return builder.toString();
	}
	
	public boolean addFoodCategory(FoodCategory newFoodCategory) {
		return foodCategories.add(newFoodCategory);
	}
//	public boolean removeFoodCategory(long id) {
//		return foodCategories.removeIf(category->category.getId()==id);
//	}


	public void addIngredient(String s) {
		ingredientEntries.add(s);
	}
}
