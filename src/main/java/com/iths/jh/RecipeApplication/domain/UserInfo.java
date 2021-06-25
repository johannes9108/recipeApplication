package com.iths.jh.RecipeApplication.domain;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserInfo {

	private String firstName;
	private String lastName;
	private int age;
	private String profileName;
	private String password;
	private LocalDate accountCreated;
	private String email;
}
