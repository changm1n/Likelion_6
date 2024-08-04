package com.example.Likelion_6_BackEnd.domain.recipe.repository;

import com.example.Likelion_6_BackEnd.domain.recipe.entity.QIngredient;
import com.example.Likelion_6_BackEnd.domain.recipe.entity.QRecipe;
import com.example.Likelion_6_BackEnd.domain.recipe.entity.QRecipeIngredient;
import com.example.Likelion_6_BackEnd.domain.recipe.entity.Recipe;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class RecipeRepositoryImpl implements RecipeRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    private final JPAQueryFactory queryFactory;

    public RecipeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public List<Recipe> findRecipesByCriteria(List<String> ingredientList, String purpose, String category, String cost) {
        QRecipe recipe = QRecipe.recipe;
        QRecipeIngredient recipeIngredient = QRecipeIngredient.recipeIngredient;
        QIngredient ingredient = QIngredient.ingredient;

        JPQLQuery<Recipe> query = queryFactory.selectFrom(recipe)
                .leftJoin(recipe.recipeIngredientList, recipeIngredient)
                .leftJoin(recipeIngredient.ingredient, ingredient)
                .distinct();

        if (ingredientList != null && !ingredientList.isEmpty()) {
            query.where(ingredient.name.in(ingredientList));
        }
        if (purpose != null && !purpose.isEmpty()) {
            query.where(recipe.purpose.eq(purpose));
        }
        if (category != null && !category.isEmpty()) {
            query.where(recipe.preference.eq(category));
        }
        if (cost != null && !cost.isEmpty()) {
            query.where(recipe.cost.eq(cost));
        }

        return query.fetch();
    }
}

