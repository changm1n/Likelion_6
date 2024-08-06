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
//        private List<String> recipeImages;
        private String intro;
        private String introUrl;
        private Integer level;
        private Integer servings;
        private String cost;
        private String purpose;
        private String preference;
        private List<List<String>> fullRecipe;
        //private List<String> ingredient;
        private List<String> content;
        private Integer requiredTime;
        private String kcal;
        private Long average;
        private String nickname;

        public RecipeCreateDTO(Recipe recipe/*,List<String> recipeImages*/,List<List<String>> fullRecipe,List<String> content){
            this.recipeId = recipe.getId();
            this.title = recipe.getTitle();
//            this.recipeImages = recipeImages;
            this.intro = recipe.getIntro();
            this.introUrl = recipe.getIntroUrl();
            this.level = recipe.getLevel();
            this.servings = recipe.getServings();
            this.cost = recipe.getCost();
            this.purpose = recipe.getPurpose();
            this.preference = recipe.getPreference();
            this.fullRecipe = fullRecipe;
            this.content = content;
            this.requiredTime = recipe.getRequiredTime();
            this.kcal = recipe.getKcal();
            this.average = Math.round(recipe.getAverage());
            this.nickname = recipe.getUser().getNickname();
        }
    }
}
