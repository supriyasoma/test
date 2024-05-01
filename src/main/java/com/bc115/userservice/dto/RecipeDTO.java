package com.bc115.userservice.dto;

import com.bc115.userservice.entity.Recipe;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long id;
    private String recipeName;
    private List<String> ingredients;
    private List<String> procedure;
    private boolean isVegetarian;
    private String thumbnailImageUrl;
    private List<String> otherImageUrls;

    private static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    public static RecipeDTO mapToDTO(Recipe recipe) {
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    public static List<RecipeDTO> mapToDTOList(List<Recipe> recipeList) {
        return recipeList.stream()
                .map(recipe -> mapToDTO(recipe))
                .collect(Collectors.toList());
    }

    public static Recipe mapToEntity(RecipeDTO recipeDTO) {
        return modelMapper.map(recipeDTO, Recipe.class);
    }
}
