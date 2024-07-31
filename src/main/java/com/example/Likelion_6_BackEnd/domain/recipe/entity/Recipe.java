package com.example.Likelion_6_BackEnd.domain.recipe.entity;

import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import com.example.Likelion_6_BackEnd.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Recipe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; //레시피 제목
    private String content; // 레시피 내용
    private Integer requiredTime; // 소요 시간
    private Integer cost; // 비용
    private Integer level; // 난이도
    private Integer servings; //인분
    private String kcal; // 칼로리
    private String intro;
    private Double average;
//    @OneToMany
//    private List<RecipeIngredient> recipeIngredientList;
    @ManyToOne
    private Member user;
    protected Recipe() {
    }

    public Recipe(String title, String intro, Integer level, Integer servings,Integer cost,Integer requiredTime){
        this.title = title;
        this.intro = intro;
        this.level = level;
        this.servings = servings;
        this.cost = cost;
        this.requiredTime = requiredTime;
    }
    public Recipe(Recipe recipe, String kcal, String content, Double average, Member user){
        this.title = recipe.getTitle();
        this.intro = recipe.getIntro();
        this.level = recipe.getLevel();
        this.servings = recipe.getServings();
        this.cost = recipe.getCost();
        this.requiredTime = recipe.getRequiredTime();
        this.content = content;
        this.kcal = kcal;
        this.average = average;
        this.user = user;
    }

    public void updateAverage(Recipe recipe, Double average){
        this.title = recipe.getTitle();
        this.content = recipe.getContent();
        this.requiredTime = recipe.getRequiredTime();
        this.cost = recipe.getCost();
        this.level = recipe.getLevel();
        this.servings = recipe.getServings();
        this.kcal = recipe.getKcal();
        this.intro = recipe.getIntro();
        this.average = average;
    }
}
