package com.iths.jh.RecipeApplication.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iths.jh.RecipeApplication.domain.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	
	@Query(value = "Select r from Recipe r"
			+ " inner join fetch r.user "
			+ " left join fetch r.quantityPerIngredient "
			+ " left join fetch r.foodCategories "
			+ "where r.id = ?1 "
			+ "")
	Optional<Recipe> findByIdFetched(Long id);

	@Query(value ="Select distinct r from Recipe r"
			+ " inner join fetch r.user "
			+ " left join fetch r.quantityPerIngredient "
			+ " left join fetch r.foodCategories ")
	List<Recipe> findAllFetched();
	
	
//	@Query(value =" Select r from Recipe r join fetch r.user.i")
//	List<Recipe> getAllRecipesFetchedWithUser();

//	@Query(value = "select new com.iths.jh.RecipeApplication.domain.RecipeInfo(r.id, r.title, r.views, r.publishedDate, r.instructions) from Recipe r")
//	Collection<Recipe> dto();
	
//	@Query(value ="SELECT r FROM Recipe r")
//	Set<Recipe> findAllRecipes();
//	
//	@Query(value = "SELECT new com.iths.jh.RecipeApplication.domain.RecipeInfo FROM Recipe r ",nativeQuery = true)
	
	
	

}
