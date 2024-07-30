package com.example.Likelion_6_BackEnd.domain.comment.dto;

import com.example.Likelion_6_BackEnd.domain.comment.entity.Comment;
import lombok.Data;

@Data
public class CommentResponseDTO {
    private Long userId;
    private String comment;
    private Integer score;
    private String image;

    public CommentResponseDTO(Comment comment,Integer score){
        this.userId = comment.getUserId();
        this.comment = comment.getComment();
        this.score = score;
        this.image = comment.getImgUrl();
    }
}
