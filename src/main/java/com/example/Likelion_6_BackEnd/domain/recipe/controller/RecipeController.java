package com.example.Likelion_6_BackEnd.domain.recipe.controller;

import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeRequestDTO;
import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeResponseDTO;
import com.example.Likelion_6_BackEnd.domain.recipe.service.RecipeServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecipeController {
    private final RecipeServiceImpl recipeService;

    //레시피 작성
    @PostMapping("/board/create")
    public ResponseEntity<?> Create(@ModelAttribute RecipeRequestDTO.RecipeCreateDTO recipeCreateDTO, HttpSession httpSession) throws IOException {
        String userEmail = (String)httpSession.getAttribute("loginEmail");
        RecipeResponseDTO.RecipeCreateDTO result = recipeService.create(recipeCreateDTO,userEmail);
        log.info(result.getNickname()+"님이 "+result.getRecipeId()+"번 글을 작성하셨습니다.");
        return ResponseEntity.ok().body("작성 성공");
    }
    // 메인레시피 조회
    @GetMapping("/board/{recipeId}")
    public ResponseEntity<?> mainRecipe(@PathVariable Long recipeId,HttpSession httpSession){
        RecipeResponseDTO.RecipeCreateDTO result = recipeService.mainRecipe(recipeId);
        String user = (String)httpSession.getAttribute("loginEmail");
        log.info(user+"님이 "+result.getRecipeId()+"번 글을 조회했습니다.");
        return ResponseEntity.ok().body(result);
    }
}
