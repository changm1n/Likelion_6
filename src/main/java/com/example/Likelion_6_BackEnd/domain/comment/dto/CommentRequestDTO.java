package com.example.Likelion_6_BackEnd.domain.comment.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommentRequestDTO {
    private String comment;
    private Integer score;
//    private MultipartFile image;
}
