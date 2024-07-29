package com.example.Likelion_6_BackEnd.domain.image.entity;

import com.example.Likelion_6_BackEnd.domain.recipe.entity.Recipe;
import com.example.Likelion_6_BackEnd.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    @ManyToOne
    private Recipe recipe;

    protected Image(){}
    public Image(String imageUrl, Recipe recipe){
        this.imageUrl = imageUrl;
        this.recipe = recipe;
    }
}
