package com.example.Likelion_6_BackEnd.domain.recipe.dto;

import com.example.Likelion_6_BackEnd.domain.recipe.entity.Recipe;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class RecipeRequestDTO {
    @Data
    public static class RecipeCreateDTO{
        private String title;
        private String intro;
        private Integer level;
        private Integer servings;
        private Integer cost;
        private List<String> ingredient;
        private List<String> content;
        private Integer requiredTime;
        private List<MultipartFile> recipeImages;

        public Recipe toEntity(){
            return new Recipe(this.title,this.intro, this.level, this.servings,this.cost, this.requiredTime);
        }
    }

    @Data
    public static class RecipeUpdateDTO{
        private String title;
        private String intro;
        private Integer level;
        private Integer servings;
        private Integer cost;

    }
}
