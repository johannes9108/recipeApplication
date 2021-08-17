package com.iths.jh.RecipeApplication.domain;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class RecipeInfo {

	private Long id;
	private String title;
	private Long views;
	private LocalDate publishedDate;
	private String instructions;
	private UserInfo user;
	public RecipeInfo(Long id, String title, Long views, LocalDate publishedDate, String instructions) {
		super();
		this.id = id;
		this.title = title;
		this.views = views;
		this.publishedDate = publishedDate;
		this.instructions = instructions;
		
	}
}
