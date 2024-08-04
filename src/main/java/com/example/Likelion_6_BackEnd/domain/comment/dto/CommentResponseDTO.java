package com.example.Likelion_6_BackEnd.domain.comment.dto;

import com.example.Likelion_6_BackEnd.domain.comment.entity.Comment;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CommentResponseDTO {
    private String nickname;
    private String comment;
    private Integer score;
    //private String image;
    private LocalDate date;

    public CommentResponseDTO(Comment comment){
        this.nickname = comment.getNickname();
        this.comment = comment.getComment();
        this.score = comment.getScore();
        //this.image = comment.getImgUrl();
        this.date = comment.getCreatedTime().toLocalDate();
    }
}
