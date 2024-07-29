package com.example.Likelion_6_BackEnd.domain.recipe.controller;

import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeRequestDTO;
import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeResponseDTO;
import com.example.Likelion_6_BackEnd.domain.recipe.service.RecipeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecipeController {
    private final RecipeServiceImpl recipeService;

    @PostMapping("/board/create")
    public ResponseEntity<?> Create(@ModelAttribute RecipeRequestDTO.RecipeCreateDTO recipeCreateDTO) throws IOException {
        RecipeResponseDTO.RecipeCreateDTO result = recipeService.create(recipeCreateDTO);
        return ResponseEntity.ok().body(result);
    }
}
