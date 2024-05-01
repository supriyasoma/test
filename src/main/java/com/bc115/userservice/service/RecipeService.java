package com.bc115.userservice.service;

import com.bc115.userservice.dto.RecipeDTO;

import java.util.List;

public interface RecipeService {
    RecipeDTO addRecipe(RecipeDTO recipeDTO,Long userId);
    RecipeDTO editRecipe(Long recipeId, RecipeDTO recipeDTO);
    void deleteRecipe(Long recipeId);
    List<RecipeDTO> getAllRecipes();
}