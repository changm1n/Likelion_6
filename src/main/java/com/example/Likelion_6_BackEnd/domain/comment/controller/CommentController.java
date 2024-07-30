package com.example.Likelion_6_BackEnd.domain.comment.controller;

import com.example.Likelion_6_BackEnd.domain.comment.dto.CommentRequestDTO;
import com.example.Likelion_6_BackEnd.domain.comment.dto.CommentResponseDTO;
import com.example.Likelion_6_BackEnd.domain.comment.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    //후기 생성
    @PostMapping("/board/{recipeId}/comment")
    public ResponseEntity<?> create(@RequestBody CommentRequestDTO commentRequestDTO, @PathVariable Long recipeId, HttpSession httpSession) throws IOException {
        String userId = (String) httpSession.getAttribute("loginId");
        CommentResponseDTO result = commentService.create(commentRequestDTO,userId,recipeId);
        return ResponseEntity.ok().body(result);
    }
}
