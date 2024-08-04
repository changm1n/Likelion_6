package com.example.Likelion_6_BackEnd.domain.recipe.controller;

import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeRequestDTO;
import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeResponseDTO;
import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeSearchDTO;
import com.example.Likelion_6_BackEnd.domain.recipe.service.RecipeServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecipeController {
    private final RecipeServiceImpl recipeService;

    //레시피 작성
    @PostMapping("/board/create")
    public ResponseEntity<?> Create(@RequestBody RecipeRequestDTO.RecipeCreateDTO recipeCreateDTO, HttpSession httpSession) throws IOException {
        String userEmail = (String)httpSession.getAttribute("loginEmail");
        RecipeResponseDTO.RecipeCreateDTO result = recipeService.create(recipeCreateDTO,userEmail);
        log.info(result.getNickname()+"님이 "+result.getRecipeId()+"번 글을 작성하셨습니다.");
        return ResponseEntity.ok().body("작성 성공");
//        return ResponseEntity.ok().body(result);
    }
    //레시피 수정

    //레시피 삭제
    @PostMapping("/board/{recipeId}/delete")
    public ResponseEntity<?> delete(@PathVariable Long recipeId){
        recipeService.delete(recipeId);
        log.info(recipeId+"번을 삭제했습니다.");
        return ResponseEntity.ok().body("삭제");
    }
    // 메인레시피 조회
    @GetMapping("/board/{recipeId}")
    public ResponseEntity<?> mainRecipe(@PathVariable Long recipeId,HttpSession httpSession){
        RecipeResponseDTO.RecipeCreateDTO result = recipeService.mainRecipe(recipeId);
        String user = (String)httpSession.getAttribute("loginEmail");
        log.info(user+"님이 "+result.getRecipeId()+"번 글을 조회했습니다.");
        return ResponseEntity.ok().body(result);
    }
    //검색 결과
    @PostMapping("/search")
    public ResponseEntity<?> searchRecipe(@RequestBody RecipeSearchDTO recipeSearchDTO){
        List<RecipeResponseDTO.RecipeCreateDTO> recipeList = recipeService.searchList(recipeSearchDTO);
        return ResponseEntity.ok().body(recipeList);
    }
    //메인 페이지
    @GetMapping("/homepage")
    public ResponseEntity<?> homePage(HttpSession session){
        String userEmail = (String)session.getAttribute("loginEmail");
        List<RecipeResponseDTO.RecipeCreateDTO> homePage = recipeService.homePage(userEmail);
        return ResponseEntity.ok().body(homePage);
    }
    //마이페이지
    @GetMapping("/mypage")
    public ResponseEntity<?> myPage(HttpSession session){
        String userEmail = (String)session.getAttribute("loginEmail");
        List<RecipeResponseDTO.RecipeCreateDTO> myRecipe = recipeService.myPage(userEmail);
        return ResponseEntity.ok().body(myRecipe);
    }
    //나의 pick 등록
    @PostMapping("/board/{recipeId}/mypick")
    public ResponseEntity<?> myPick(HttpSession session, @PathVariable Long recipeId){
        String userEmail = (String)session.getAttribute("loginEmail");
        Long addRecipeId = recipeService.myPick(recipeId,userEmail);
        return ResponseEntity.ok().body(addRecipeId);
    }
    // 나의 pick 조회
    @GetMapping("/user/mypick")
    public ResponseEntity<?> myPick(HttpSession session){
        String userEmail = (String) session.getAttribute("loginEmail");
        List<RecipeResponseDTO.RecipeCreateDTO> myPickList = recipeService.myPickList(userEmail);
        return ResponseEntity.ok().body(myPickList);
    }
}
