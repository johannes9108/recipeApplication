package com.iths.jh.RecipeApplication.rest;

import java.util.List;

import com.github.fge.jsonpatch.JsonPatch;
import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.services.ServiceInterface;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import com.iths.jh.RecipeApplication.utilities.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iths.jh.RecipeApplication.domain.User;
import com.iths.jh.RecipeApplication.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private ServiceInterface<User> userService;


    @GetMapping
    @RequestMapping(value = "{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        ServiceResponse<User> response = userService.findById(id);
        if (response.isSucessful()) {
            return ResponseEntity.ok(response.getResponseObject());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
		ServiceResponse<User> response = userService.findAll(page,size);
		if (response.isSucessful()) {
//            response.getResponseObjects().forEach((user) -> System.out.println("User: " + user.getUser().fullName()));
			return ResponseEntity.ok(response.getResponseObjects());
		}
		else{
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	}

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "search")
    public ResponseEntity<List<User>> getAllUsers(@RequestBody SearchParams searchParams, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        logger.warn(searchParams.toString());
        System.out.println("page: " + page + ", size: " + size );
        ServiceResponse<User> response = userService.findAll(searchParams, page, size);
        if (response.isSucessful()) {
            return ResponseEntity.ok(response.getResponseObjects());
        }
        else{
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody @Valid User newUser) {
        logger.debug("Inside POST User");

        ServiceResponse<User> response = userService.create(newUser);
        if (response.isSucessful()) {
            logger.info(response.toString());
            return ResponseEntity.ok(response.getResponseObject());
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody User newUser) {

        ServiceResponse<User> response = userService.update(newUser);
        if (response.isSucessful()) {
            return ResponseEntity.ok(response.getResponseObject());
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(path = "{id}", consumes = "application/json-patch+json",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody JsonPatch patch) {
        ServiceResponse<User> response = userService.patch(id,patch);
        if (response.isSucessful()) {
            return ResponseEntity.ok(response.getResponseObject());
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping(value = "{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        ServiceResponse<User> response = userService.deleteById(id);
        if (response.isSucessful()) {
            return ResponseEntity.ok(response.getResponseObject());
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
	
}
