package com.example.Likelion_6_BackEnd.domain.recipe.dto;

import com.example.Likelion_6_BackEnd.domain.recipe.entity.Recipe;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class RecipeResponseDTO {
    @Data
    public static class RecipeCreateDTO{
        private Long recipeId;
        private String title;
        private List<String> recipeImages;
        private String intro;
        private Integer level;
        private Integer servings;
        private Integer cost;
        private List<String> ingredient;
        private List<String> content;
        private Integer requiredTime;
        private String kcal;

        public RecipeCreateDTO(Recipe recipe,List<String> recipeImages,List<String> ingredient,List<String> content){
            this.recipeId = recipe.getId();
            this.title = recipe.getTitle();
            this.recipeImages = recipeImages;
            this.intro = recipe.getIntro();
            this.level = recipe.getLevel();
            this.servings = recipe.getServings();
            this.cost = recipe.getCost();
            this.ingredient = ingredient;
            this.content = content;
            this.requiredTime = recipe.getRequiredTime();
            this.kcal = recipe.getKcal();
        }
    }
}
