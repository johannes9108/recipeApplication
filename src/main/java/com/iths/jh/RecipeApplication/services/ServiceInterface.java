package com.iths.jh.RecipeApplication.services;

import java.util.List;
import java.util.Optional;

import com.iths.jh.RecipeApplication.domain.Recipe;

public interface ServiceInterface<T> {
	
	public Optional<T> findById(Long id);
	public List<T> findAll();
	public Optional<T> deleteById(Long id);
	public Optional<T> updateById(T newData);
	public Optional<T> create(T newData);
}
