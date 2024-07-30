package com.example.Likelion_6_BackEnd.domain.image.repository;

import com.example.Likelion_6_BackEnd.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i.imageUrl FROM Image i WHERE i.recipe.id = :recipeId")
    List<String> findImageUrlsByRecipeId(@Param("recipeId") Long recipeId);
}
