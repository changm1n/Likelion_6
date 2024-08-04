package com.example.Likelion_6_BackEnd.domain.recipe.repository;

import com.example.Likelion_6_BackEnd.domain.recipe.entity.Recipe;

import java.util.List;

public interface RecipeRepositoryCustom {
    List<Recipe> findRecipesByCriteria(List<String> ingredientList, String purpose, String category, String cost);
}
