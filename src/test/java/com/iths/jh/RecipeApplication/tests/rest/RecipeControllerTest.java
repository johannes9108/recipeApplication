package com.iths.jh.RecipeApplication.tests.rest;

import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.rest.RecipeController;
import com.iths.jh.RecipeApplication.services.ServiceInterface;
import com.iths.jh.RecipeApplication.utilities.ServiceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({RecipeController.class})
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    Logger logger = LoggerFactory.getLogger(RecipeControllerTest.class);

    private ServiceResponse<Recipe> response;

    @BeforeEach
    private void init(){
        response = new ServiceResponse<>();
    }

    @MockBean
    private ServiceInterface<Recipe> recipeService;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void getRecipeByIdTest() throws Exception {

        response.setResponseObject(null);
        when(recipeService.findById(anyLong())).thenReturn(response);
        this.mockMvc.perform(get("/recipes/{id}",1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        response.addErrorMessage("");
        when(recipeService.findById(anyLong())).thenReturn(response);
        this.mockMvc.perform(get("/recipes/{id}", 0))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllRecipesTest() throws Exception {
        ServiceResponse<Recipe> response = new ServiceResponse<>();
        response.setResponseObject(null);
        when(recipeService.findAll(anyInt(), anyInt())).thenReturn(response);
        this.mockMvc.perform(get("/recipes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllRecipesFilteredTest() throws Exception {
        ServiceResponse<Recipe> response = new ServiceResponse<>();
        response.setResponseObject(null);
        when(recipeService.findAll(anyInt(), anyInt())).thenReturn(response);
        this.mockMvc.perform(get("/recipes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postRecipeTest() throws Exception {
        ServiceResponse<Recipe> response = new ServiceResponse<>();
        response.setResponseObject(null);
        when(recipeService.findAll(anyInt(), anyInt())).thenReturn(response);
        this.mockMvc.perform(get("/recipes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateRecipeTest() throws Exception {
        ServiceResponse<Recipe> response = new ServiceResponse<>();
        response.setResponseObject(null);
        when(recipeService.findAll(anyInt(), anyInt())).thenReturn(response);
        this.mockMvc.perform(get("/recipes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void patchRecipeTest() throws Exception {
        ServiceResponse<Recipe> response = new ServiceResponse<>();
        response.setResponseObject(null);
        when(recipeService.findAll(anyInt(), anyInt())).thenReturn(response);
        this.mockMvc.perform(get("/recipes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRecipeTest() throws Exception {
        ServiceResponse<Recipe> response = new ServiceResponse<>();
        response.setResponseObject(null);
        when(recipeService.findAll(anyInt(), anyInt())).thenReturn(response);
        this.mockMvc.perform(get("/recipes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
