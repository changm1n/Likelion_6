package com.example.Likelion_6_BackEnd.domain.comment.repository;

import com.example.Likelion_6_BackEnd.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
