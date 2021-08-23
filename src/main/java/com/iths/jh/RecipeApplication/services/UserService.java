package com.iths.jh.RecipeApplication.services;

import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import com.iths.jh.RecipeApplication.utilities.ServiceErrorMessages;
import com.iths.jh.RecipeApplication.utilities.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iths.jh.RecipeApplication.domain.User;
import com.iths.jh.RecipeApplication.repositories.UserRepository;

@Service
@Slf4j
public class UserService implements ServiceInterface<User> {

	@Autowired
	UserRepository userRepository;


	@Override
	public ServiceResponse<User> findById(Long id) {
		ServiceResponse<User> response = new ServiceResponse<User>();

		try {
			System.out.println("Return User with id: " + id);
			User user = userRepository.findByIdFetched(id).orElseThrow(NoSuchElementException::new);
			response.setResponseObject(user);
		} catch (Exception e) {
			response.addErrorMessage(e.getLocalizedMessage());
		}
		return response;
	}

	@Override
	public ServiceResponse<User> findAll(int page, int size) {
		ServiceResponse<User> response = new ServiceResponse<User>();
		try {
			System.out.println("page: " + page + ", size: " + size);
			Pageable pageable = PageRequest.of(page>=1?page-1:0, Math.max(size, 1));
			List<User> listOfUsers = userRepository.findAllFetched(pageable);
			System.out.println(listOfUsers.size());

			response.setResponseObjects(listOfUsers);
		} catch (Exception e) {
			response.addErrorMessage(e.getLocalizedMessage());
		}
		return response;
	}

	@Override
	public ServiceResponse<User> findAll(SearchParams searchParams, int page, int size) {
		ServiceResponse<User> response = new ServiceResponse<User>();
		try {
			System.out.println("Return all Users");
			Pageable pageable = PageRequest.of(page>=1?page-1:0, Math.max(size, 1));
			Page<User> pagedUsers = userRepository.findAllFetched(searchParams, pageable);
			response.setResponseObjects(pagedUsers.getContent());
		} catch (Exception e) {
			response.addErrorMessage(e.getLocalizedMessage());
		}
		return response;
	}

	@Override
	public ServiceResponse<User> create(User newData) {
		ServiceResponse<User> response = new ServiceResponse<>();
		try {
			// Prevents an accidental update by discarding the id
			newData.setId(null);
			User user = userRepository.save(newData);
			response.setResponseObject(user);
		} catch (Exception e) {
			response.addErrorMessage(e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
		}
		return response;
	}

	@Override
	public ServiceResponse<User> update(User newData) {
		ServiceResponse<User> response = new ServiceResponse<>();
		try {
			User userToBeUpdated = userRepository.findByIdFetched(newData.getId()).orElseThrow(NoSuchElementException::new);
			userToBeUpdated = newData;
			User user = userRepository.save(userToBeUpdated);
			System.out.println("Updated user with id: " + user.getId());
			response.setResponseObject(user);
		} catch (Exception e) {
			response.addErrorMessage(e.getLocalizedMessage());
			log.error(e.getLocalizedMessage());
		}
		return response;
	}


	@Override
	public ServiceResponse<User> patch(Long id, JsonPatch patch) {
		ServiceResponse<User> response = new ServiceResponse<>();
		try {
			User recipe = userRepository.findByIdFetched(id).orElseThrow(NoSuchElementException::new);
			User recipePatched = applyPatchToUser(patch, recipe);
			log.info("Updated recipe: " + recipePatched.toString());
			System.out.println("Updated recipe: " + recipePatched.toString());
			ServiceResponse<User> op = update(recipePatched);
			if (op.isSucessful()) {
				response.setResponseObject(op.getResponseObject());
			} else {
				response.addErrorMessage(ServiceErrorMessages.RECIPE.couldNotUpdate(id));
			}
		} catch (Exception e) {
			response.addErrorMessage(e.getLocalizedMessage());
		}
		return response;
	}

	private User applyPatchToUser(
			JsonPatch patch, User targetUser) throws JsonPatchException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode patched = patch.apply(objectMapper.convertValue(targetUser, JsonNode.class));
		return objectMapper.treeToValue(patched, User.class);
	}

	@Override
	public ServiceResponse<User> deleteById(Long id) {
		ServiceResponse<User> response = new ServiceResponse<>();
		Optional<User> user = userRepository.findByIdFetched(id);
		if (user.isPresent()) {
			System.out.println("User with id to be deleted: " + user.get().getId());
			userRepository.deleteById(id);
			response.setResponseObject(user.get());
		} else {
			response.addErrorMessage(ServiceErrorMessages.RECIPE.couldNotFind(id));
		}
		return response;
	}
}
