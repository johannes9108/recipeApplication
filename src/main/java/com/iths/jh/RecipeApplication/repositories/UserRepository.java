package com.iths.jh.RecipeApplication.repositories;

import java.util.List;
import java.util.Optional;

import com.iths.jh.RecipeApplication.repositories.customQueries.RecipeRepositoryCustom;
import com.iths.jh.RecipeApplication.repositories.customQueries.UserRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iths.jh.RecipeApplication.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
	
	@Query("select u from User u "
			+ "inner join fetch u.recipes r "
			+ "inner join fetch r.foodCategories fc "
			+ "where u.id = ?1")
	Optional<User> findByIdFetched(long id);

	@Query("select u from User u "
			+ "left join fetch u.recipes r "
			)
	List<User> findAllFetched(Pageable pageable);


}
