package com.iths.jh.RecipeApplication.domain;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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
