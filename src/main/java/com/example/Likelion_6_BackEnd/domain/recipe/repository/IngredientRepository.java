package com.example.Likelion_6_BackEnd.domain.recipe.repository;

import com.example.Likelion_6_BackEnd.domain.recipe.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByNameIn(List<String> names);
    Optional<Ingredient> findByName(String name);
}
