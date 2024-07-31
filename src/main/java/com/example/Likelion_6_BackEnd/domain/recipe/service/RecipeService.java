package com.example.Likelion_6_BackEnd.domain.recipe.service;

import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeRequestDTO;
import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeResponseDTO;

import java.io.IOException;

public interface RecipeService {
    //레시피 생성
    RecipeResponseDTO.RecipeCreateDTO create(RecipeRequestDTO.RecipeCreateDTO recipeCreateDTO) throws IOException;
    //레시피 수정

    //레시피 삭제

    //레시피 조회
    RecipeResponseDTO.RecipeCreateDTO mainRecipe(Long recipeId);
    //레시피 검색결과

    //칼로리 api
    String kcal(String menu);
}
