package com.bc115.userservice.controller;

import com.bc115.userservice.dto.RecipeDTO;
import com.bc115.userservice.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

   @PostMapping
public ResponseEntity<RecipeDTO> addRecipe(@RequestParam("recipeName") String recipeName,
                                           @RequestParam("ingredients") List<String> ingredients,
                                           @RequestParam("procedure") List<String> procedure,
                                           @RequestParam("isVegetarian") boolean isVegetarian,
                                           @RequestParam("thumbnailImage") MultipartFile thumbnailImage,
                                           @RequestParam("otherImages") List<MultipartFile> otherImages,
                                           @RequestParam("userId") Long userId) {

    // Create a new RecipeDTO and set its attributes using the provided parameters
    RecipeDTO recipeDTO = new RecipeDTO();
    recipeDTO.setRecipeName(recipeName);
    recipeDTO.setIngredients(ingredients);
    recipeDTO.setProcedure(procedure);
    recipeDTO.setVegetarian(isVegetarian);

    // Save thumbnail image
    String thumbnailImageUrl = saveImage(thumbnailImage);
    recipeDTO.setThumbnailImageUrl(thumbnailImageUrl);

    // Save other images
    List<String> otherImageUrls = new ArrayList<>();
    for (MultipartFile image : otherImages) {
        otherImageUrls.add(saveImage(image));
    }
    recipeDTO.setOtherImageUrls(otherImageUrls);

    // Call the service method to add the recipe
    RecipeDTO createdRecipe = recipeService.addRecipe(recipeDTO, userId);

    // Return the created recipe DTO in the response
    return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
}

    // Method to save image
    private String saveImage(MultipartFile image) {
        // Save the image to your desired location or database
        // For simplicity, let's assume we're saving it to a folder named "uploads" in the current directory
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        Path path = Paths.get("uploads").resolve(fileName).normalize();
        try {
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return path.toString(); // Return the file path as the image URL
        } catch (IOException ex) {
            throw new RuntimeException("Failed to save image: " + ex.getMessage());
        }
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<RecipeDTO> editRecipe(@PathVariable Long recipeId, @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO updatedRecipe = recipeService.editRecipe(recipeId, recipeDTO);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }
}

