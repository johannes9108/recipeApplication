package com.iths.jh.RecipeApplication.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="users")
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	private String lastName;
	private int age;
	private String profileName;
	private String password;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate accountCreated;
	private String email;

	@JsonIgnore
	@OneToMany (mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<Recipe> recipes;


	public User(String firstName, String lastName, int age, String profileName, String password,
				LocalDate accountCreated, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.profileName = profileName;
		this.password = password;
		this.accountCreated = accountCreated;
		this.email = email;
		recipes = new HashSet<Recipe>();
		
	}


	public Set<Recipe> getRecipes() {
		if(recipes==null)
			recipes = new HashSet<Recipe>();
		return recipes;
	}


	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", profileName=" + profileName + ", password=" + password + ", accountCreated=" + accountCreated
				+ ", email=" + email + ", recipes=" + formattedRecipes() + "]";
	}
	
	private String formattedRecipes() {
		StringBuilder builder = new  StringBuilder("");
		int counter = 1;
		for(Recipe r: recipes) {
			builder.append("|" + counter++ + ":" +r.getTitle());
		}
		builder.append("|");
		return builder.toString();
		
	}
	
	public String fullName() {
		return this.firstName + " " + this.lastName;
	}
	
	public Recipe addRecipe(Recipe recipe) throws Exception {
		try {
			this.recipes.add(recipe);
			recipe.setUser(this);
			return recipe;
		}
		catch(Exception e) {
			throw new Exception("Couldn't add new Recipe");
		}
	}
	
	
	
}
