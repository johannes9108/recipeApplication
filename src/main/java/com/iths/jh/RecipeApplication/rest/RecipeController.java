package com.iths.jh.RecipeApplication.rest;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.iths.jh.RecipeApplication.domain.dto.DTOUtilities;
import com.iths.jh.RecipeApplication.domain.dto.RecipeDTO;
import com.iths.jh.RecipeApplication.services.ServiceInterface;
import com.iths.jh.RecipeApplication.utilities.SearchParams;
import com.iths.jh.RecipeApplication.utilities.ServiceResponse;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.services.RecipeService;

import javax.validation.Valid;

@RestController
@RequestMapping("recipes")
public class RecipeController {

    @Autowired
    private ServiceInterface<Recipe> recipeService;

    Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @RequestMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        ServiceResponse<Recipe> response = recipeService.findById(id);
        if (response.isSucessful()) {
            return ResponseEntity.ok(DTOUtilities.convertToDto(response.getResponseObject(), RecipeDTO.class, modelMapper));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<RecipeDTO>> getAllRecipes(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        ServiceResponse<Recipe> response = recipeService.findAll(page, size);
        if (response.isSucessful()) {
            response.getResponseObjects().forEach((recipe) -> System.out.println("User: " + recipe.getUser().fullName()));
            return ResponseEntity.ok(response.getResponseObjects().stream().map(entity -> {
                        return DTOUtilities.convertToDto(entity, RecipeDTO.class,modelMapper);
                    }
            ).collect(Collectors.toList()));
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "search")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes(@RequestBody SearchParams searchParams, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        logger.warn(searchParams.toString());
        System.out.println("page: " + page + ", size: " + size);
        ServiceResponse<Recipe> response = recipeService.findAll(searchParams, page, size);
        if (response.isSucessful()) {
            response.getResponseObjects().forEach((recipe) -> System.out.println("User: " + recipe.getUser().fullName()));
            return ResponseEntity.ok(response.getResponseObjects().stream().map(entity -> {
                return DTOUtilities.convertToDto(entity, RecipeDTO.class,modelMapper);
            }).collect(Collectors.toList()));
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> createRecipe(@RequestBody @Valid Recipe newRecipe) {
        logger.debug("Inside POST Recipe");

        ServiceResponse<Recipe> response = recipeService.create(newRecipe);
        if (response.isSucessful()) {
            logger.info(response.toString());
            return ResponseEntity.ok(response.getResponseObject());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe newRecipe) {

        ServiceResponse<Recipe> response = recipeService.update(newRecipe);
        if (response.isSucessful()) {
            return ResponseEntity.ok(response.getResponseObject());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(path = "{id}", consumes = "application/json-patch+json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Recipe> patchRecipe(@PathVariable Long id, @RequestBody JsonPatch patch) {
        ServiceResponse<Recipe> response = recipeService.patch(id, patch);
        if (response.isSucessful()) {
            return ResponseEntity.ok(response.getResponseObject());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        ServiceResponse<Recipe> response = recipeService.deleteById(id);
        if (response.isSucessful()) {
            return ResponseEntity.ok(response.getResponseObject());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
