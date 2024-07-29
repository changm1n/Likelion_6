package com.example.Likelion_6_BackEnd.domain.image.repository;

import com.example.Likelion_6_BackEnd.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
