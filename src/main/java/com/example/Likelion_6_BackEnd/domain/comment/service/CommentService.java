package com.example.Likelion_6_BackEnd.domain.comment.service;

import com.example.Likelion_6_BackEnd.domain.comment.dto.CommentRequestDTO;
import com.example.Likelion_6_BackEnd.domain.comment.dto.CommentResponseDTO;
import com.example.Likelion_6_BackEnd.domain.comment.entity.Comment;
import com.example.Likelion_6_BackEnd.domain.comment.repository.CommentRepository;
import com.example.Likelion_6_BackEnd.domain.recipe.entity.Recipe;
import com.example.Likelion_6_BackEnd.domain.recipe.repository.RecipeRepository;
import com.example.Likelion_6_BackEnd.domain.recipe.service.RecipeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;
    private final RecipeServiceImpl recipeService;

    //후기 생성
    public CommentResponseDTO create(CommentRequestDTO commentRequestDTO, Long userId,Long recipeId) throws IOException {
        Optional<Recipe> recipe1 = recipeRepository.findById(recipeId);
        String imgUrl = recipeService.upload(commentRequestDTO.getImage());
        Integer score = commentRequestDTO.getScore();
        if(recipe1.isPresent()){
            Recipe recipe = recipe1.get();
            Comment comment = new Comment(commentRequestDTO.getComment(), recipe ,userId,imgUrl);
            commentRepository.save(comment);
            return new CommentResponseDTO(comment, score);
        }
        else{
            return null;
        }
    }
}
