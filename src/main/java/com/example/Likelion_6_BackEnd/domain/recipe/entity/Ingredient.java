package com.example.Likelion_6_BackEnd.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    private List<RecipeIngredient> recipeIngredientList;

    protected Ingredient(){
    }

    public Ingredient(String name){
        this.name = name;
    }
}
