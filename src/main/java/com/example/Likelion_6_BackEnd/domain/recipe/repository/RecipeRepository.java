package com.example.Likelion_6_BackEnd.domain.recipe.repository;

import com.example.Likelion_6_BackEnd.domain.recipe.entity.Ingredient;
import com.example.Likelion_6_BackEnd.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
//    @Query("SELECT r FROM Recipe r WHERE NOT EXISTS (" +
//            "SELECT ri FROM RecipeIngredient ri WHERE ri.recipe = r AND ri.ingredient IN :ingredients)")
//    List<Recipe> findAllWithoutIngredients(@Param("ingredients") List<Ingredient> ingredients);
    Optional<Recipe> findById(Long recipeId);
    @Query("SELECT r.content FROM Recipe r WHERE r.id = :recipeId")
    String findContentByRecipeId(@Param("recipeId") Long recipeId);
    @Query("SELECT ri.ingredient.name FROM RecipeIngredient ri WHERE ri.recipe.id = :recipeId")
    List<String> findIngredientNamesByRecipeId(@Param("recipeId") Long recipeId);
}
