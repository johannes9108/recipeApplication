package com.iths.jh.RecipeApplication.repositories;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.iths.jh.RecipeApplication.repositories.customQueries.RecipeRepositoryCustom;
import com.iths.jh.RecipeApplication.utilities.ServiceErrorMessages;
import com.iths.jh.RecipeApplication.utilities.ServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iths.jh.RecipeApplication.domain.Recipe;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepositoryCustom {
	
	@Query(value = "Select r from Recipe r"
			+ " inner join fetch r.user "
			+ " left join fetch r.foodCategories "
			+ "where r.id = ?1 "
			+ "")
	Optional<Recipe> findByIdFetched(Long id);

	@Query(value ="Select distinct r from Recipe r"
			+ " inner join fetch r.user "
			+ " left join fetch r.foodCategories ")
	List<Recipe> findAllFetched(Pageable pageable);

	@Override
	Page<Recipe> findAll(Pageable pageable);

	default ServiceResponse<Recipe> deleteByIdWithReturnValue(Long id){
		Optional<Recipe> deleteObject = findByIdFetched(id);
		if (deleteObject.isPresent()) {
			deleteById(id);
			return new ServiceResponse<Recipe>(deleteObject.get(), null);
		}
		return new ServiceResponse<>(null, Collections.singletonList(ServiceErrorMessages.RECIPE.couldNotFind(id)));
	}

	//	@Query(value =" Select r from Recipe r join fetch r.user.i")
//	List<Recipe> getAllRecipesFetchedWithUser();

//	@Query(value = "select new com.iths.jh.RecipeApplication.domain.RecipeInfo(r.id, r.title, r.views, r.publishedDate, r.instructions) from Recipe r")
//	Collection<Recipe> dto();
	
//	@Query(value ="SELECT r FROM Recipe r")
//	Set<Recipe> findAllRecipes();
//	
//	@Query(value = "SELECT new com.iths.jh.RecipeApplication.domain.RecipeInfo FROM Recipe r ",nativeQuery = true)
	
	
	

}
