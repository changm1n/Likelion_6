package com.example.Likelion_6_BackEnd.domain.recipe.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeSearchDTO {
    private List<String> ingredientList;
    private String purpose;
    private String category;
    private String cost;
}
