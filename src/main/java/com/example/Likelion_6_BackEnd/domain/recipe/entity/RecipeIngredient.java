package com.example.Likelion_6_BackEnd.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Recipe recipe;
    @ManyToOne
    private Ingredient ingredient;

    protected RecipeIngredient(){
    }

    public RecipeIngredient(Recipe recipe, Ingredient ingredient){
        this.recipe = recipe;
        this.ingredient = ingredient;
    }
}
