package com.example.Likelion_6_BackEnd.domain.comment.entity;

import com.example.Likelion_6_BackEnd.domain.recipe.entity.Recipe;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String comment;
    private String imgUrl;
    private Integer score;
    @ManyToOne
    private Recipe recipe;

    protected Comment(){}

    public Comment(String comment, Integer score, Recipe recipe,String userId, String imgUrl){
        this.comment = comment;
        this.score = score;
        this.recipe = recipe;
        this.nickname = userId;
        this.imgUrl = imgUrl;
    }
}
