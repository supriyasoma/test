package com.bc115.userservice.service;

import com.bc115.userservice.dto.RecipeDTO;
import com.bc115.userservice.entity.Recipe;
import com.bc115.userservice.entity.User;
import com.bc115.userservice.repository.RecipeRepository;
import com.bc115.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RecipeDTO addRecipe(RecipeDTO recipeDTO,Long userId) {
        Recipe recipe = RecipeDTO.mapToEntity(recipeDTO);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the User for the Recipe
        recipe.setUser(user);

        Recipe savedRecipe = recipeRepository.save(recipe);
        return RecipeDTO.mapToDTO(savedRecipe);
    }

    @Override
    public RecipeDTO editRecipe(Long recipeId, RecipeDTO recipeDTO) {
        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        existingRecipe.setRecipeName(recipeDTO.getRecipeName());
        existingRecipe.setIngredients(recipeDTO.getIngredients());
        existingRecipe.setProcedure(recipeDTO.getProcedure());
        existingRecipe.setVegetarian(recipeDTO.isVegetarian());
        existingRecipe.setThumbnailImageUrl(recipeDTO.getThumbnailImageUrl());
        existingRecipe.setOtherImageUrls(recipeDTO.getOtherImageUrls());

        Recipe updatedRecipe = recipeRepository.save(existingRecipe);
        return RecipeDTO.mapToDTO(updatedRecipe);
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    @Override
    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return RecipeDTO.mapToDTOList(recipes);
    }
}

