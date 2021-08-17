package com.iths.jh.RecipeApplication.services;

import java.util.List;
import java.util.Optional;

import com.github.fge.jsonpatch.JsonPatch;
import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import com.iths.jh.RecipeApplication.utilities.ServiceResponse;

public interface ServiceInterface<T> {
	
	public  ServiceResponse<T> findById(Long id);
	public  ServiceResponse<T> findAll();
	public  ServiceResponse<T> deleteById(Long id);
	public  ServiceResponse<T> update(T newData);
	public ServiceResponse<T> create(T newData);
	public ServiceResponse<T> patch(Long id, JsonPatch patch);

    public  ServiceResponse<T> findAll(SearchParams searchParams);
}
