package com.example.Likelion_6_BackEnd.domain.recipe.repository;

import com.example.Likelion_6_BackEnd.domain.recipe.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
}
