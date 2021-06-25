package com.iths.jh.RecipeApplication.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iths.jh.RecipeApplication.domain.User;
import com.iths.jh.RecipeApplication.services.UserService;
@RestController
@RequestMapping("users")
public class UserController {

//	@Autowired
//	private UserService userSerivce;
//	
//	@GetMapping
//	public List<User> getAllUsers() {
//		return userSerivce.findAll();
//	}
//	
//	@GetMapping
//	@RequestMapping("{id}")
//	public String getUser(@PathVariable int id) {
//		System.out.println("User with id: " + id);
//		return "User with id: "+id;
//	}
	
}
