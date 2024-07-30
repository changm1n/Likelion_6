package com.example.Likelion_6_BackEnd.domain.comment.controller;

import com.example.Likelion_6_BackEnd.domain.comment.dto.CommentRequestDTO;
import com.example.Likelion_6_BackEnd.domain.comment.dto.CommentResponseDTO;
import com.example.Likelion_6_BackEnd.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/comment/create")
    public ResponseEntity<?> create(@RequestBody CommentRequestDTO commentRequestDTO){
        // 유저 아이디
        // 레시피 아이디
        CommentResponseDTO result = commentService.create(commentRequestDTO,)
    }
}
