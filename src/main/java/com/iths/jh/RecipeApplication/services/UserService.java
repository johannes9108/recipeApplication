package com.iths.jh.RecipeApplication.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iths.jh.RecipeApplication.domain.User;
import com.iths.jh.RecipeApplication.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	private HashSet<User> users = new HashSet<User>();
	{
		User newUser = new User("Johannes", "Hedman", 10,"jh","1234",LocalDate.now(), "mail@mail.com");
		users.add(newUser);
		newUser = new User("Kalle", "Svensson", 10,"jh","1234",LocalDate.now(), "mail@mail.com");
		users.add(newUser);
		newUser = new User("Ali", "Abduzeedo", 10,"jh","1234",LocalDate.now(), "mail@mail.com");
		users.add(newUser);
		newUser = new User("Karin", "Rovén", 10,"jh","1234",LocalDate.now(), "mail@mail.com");
		users.add(newUser);

	}

	public List<User> findAll() {
		System.out.println("Return all Users");
		return userRepository.findAll();
//		return new ResponseEntity<Set<User>>(new HashSet<User>(recipeServce.),HttpStatus.OK) ;
//		return new ResponseEntity<Set<User>>(new HashSet<User>(userRepository.findAll()),HttpStatus.OK) ;
	}
}
