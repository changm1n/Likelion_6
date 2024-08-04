package com.example.Likelion_6_BackEnd.domain.comment.service;

import com.example.Likelion_6_BackEnd.domain.comment.dto.CommentRequestDTO;
import com.example.Likelion_6_BackEnd.domain.comment.dto.CommentResponseDTO;
import com.example.Likelion_6_BackEnd.domain.comment.entity.Comment;
import com.example.Likelion_6_BackEnd.domain.comment.repository.CommentRepository;
import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import com.example.Likelion_6_BackEnd.domain.member.repository.MemberRepository;
import com.example.Likelion_6_BackEnd.domain.recipe.entity.Recipe;
import com.example.Likelion_6_BackEnd.domain.recipe.repository.RecipeRepository;
import com.example.Likelion_6_BackEnd.domain.recipe.service.RecipeService;
import com.example.Likelion_6_BackEnd.domain.recipe.service.RecipeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    private final RecipeServiceImpl recipeService;

    @Transactional
    //후기 생성
    public CommentResponseDTO create(CommentRequestDTO commentRequestDTO, String userId,Long recipeId) throws IOException {
        Optional<Recipe> recipe1 = recipeRepository.findById(recipeId);
        //String imgUrl = recipeService.upload(commentRequestDTO.getImage());
        Optional<Member> member = memberRepository.findByuserEmail(userId);
        String nickname = member.get().getNickname();
        Integer score = commentRequestDTO.getScore();
        if(recipe1.isPresent()){
            Recipe recipe = recipe1.get();
            Comment comment = new Comment(commentRequestDTO.getComment(), commentRequestDTO.getScore() ,recipe ,nickname/*,imgUrl*/);
            commentRepository.save(comment);
            return new CommentResponseDTO(comment);
        }
        else{
            return null;
        }
    }
    //후기 조회
    public List<CommentResponseDTO> commentList(Long recipeId){
        List<Comment> comments = commentRepository.findByrecipeId(recipeId);
        return comments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CommentResponseDTO convertToDTO(Comment comment){
        return new CommentResponseDTO(comment);
    }
    @Transactional
    //후기 삭제
    public String delete(Long commentId){
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if(commentOptional.isPresent()){
            Comment comment = commentOptional.get();
            commentRepository.delete(comment);
            log.info("삭제했습니다.");
            return "삭제했습니다.";
        }
        else{
            return null;
        }
    }
}
