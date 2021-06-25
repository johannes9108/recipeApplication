package com.iths.jh.RecipeApplication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iths.jh.RecipeApplication.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select u from User u "
			+ "inner join fetch u.recipes r "
			+ "inner join fetch r.quantityPerIngredient i "
			+ "inner join fetch r.foodCategories fc "
			+ "where u.id = ?1")
	Optional<User> findByIdFetched(long id);

}
