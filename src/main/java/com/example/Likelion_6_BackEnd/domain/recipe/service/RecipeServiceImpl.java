package com.example.Likelion_6_BackEnd.domain.recipe.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.Likelion_6_BackEnd.domain.comment.entity.Comment;
import com.example.Likelion_6_BackEnd.domain.comment.repository.CommentRepository;
import com.example.Likelion_6_BackEnd.domain.comment.service.CommentService;
import com.example.Likelion_6_BackEnd.domain.image.entity.Image;
import com.example.Likelion_6_BackEnd.domain.image.repository.ImageRepository;
import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import com.example.Likelion_6_BackEnd.domain.member.repository.MemberRepository;
import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeRequestDTO;
import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeResponseDTO;
import com.example.Likelion_6_BackEnd.domain.recipe.dto.RecipeSearchDTO;
import com.example.Likelion_6_BackEnd.domain.recipe.entity.Ingredient;
import com.example.Likelion_6_BackEnd.domain.recipe.entity.Recipe;
import com.example.Likelion_6_BackEnd.domain.recipe.entity.RecipeIngredient;
import com.example.Likelion_6_BackEnd.domain.recipe.repository.IngredientRepository;
import com.example.Likelion_6_BackEnd.domain.recipe.repository.RecipeIngredientRepository;
import com.example.Likelion_6_BackEnd.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    private final AmazonS3 amazonS3;
    @Value("yoribogo")
    private String bucket;

    //게시글 생성
    @Transactional
    @Override
    public RecipeResponseDTO.RecipeCreateDTO create(RecipeRequestDTO.RecipeCreateDTO recipeCreateDTO,String userEmail) throws IOException{
        Recipe recipe1 = recipeCreateDTO.toEntity();
        Optional<Member> memberOptional = memberRepository.findByuserEmail(userEmail);
        Member user = memberOptional.get();
        List<String> recipeList = recipeCreateDTO.getContent();
        //레시피 단계 하나로 합치기
        String combineString = String.join("\n", recipeList);
        //api로 칼로리 가져오기
        String kcal = "abc";
        // 평점 가져오기
        Double average = 0.0;
        Recipe recipe = new Recipe(recipe1, kcal, combineString,average,user);
        recipeRepository.save(recipe);
        //재료 저장
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        for (String ingredientName : recipeCreateDTO.getIngredient()){
            Ingredient ingredient = ingredientRepository.findByName(ingredientName)
                    .orElseGet(() -> {
                Ingredient newIngredient = new Ingredient(ingredientName);
                return ingredientRepository.save(newIngredient);
            });

            RecipeIngredient recipeIngredient = new RecipeIngredient(recipe, ingredient);
            recipeIngredients.add(recipeIngredient);
            //recipe.getRecipeIngredientList().add(recipeIngredient);
            //ingredient.getRecipeIngredientList().add(recipeIngredient);
        }
        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            recipeIngredientRepository.save(recipeIngredient);
        }
        //사진 저장
        List<MultipartFile> recipeImageList = recipeCreateDTO.getRecipeImages();
        List<String> imgUrlList = new ArrayList<>();
        for(MultipartFile imageFile : recipeImageList){
            if(imageFile != null && !imageFile.isEmpty()){
                String imgUrl = upload(imageFile);
                imgUrlList.add(imgUrl);
                Image image = new Image(imgUrl, recipe);
                imageRepository.save(image);
            }
        }
        return new RecipeResponseDTO.RecipeCreateDTO(recipe,imgUrlList,recipeCreateDTO.getIngredient(),recipeList);
    }
    @Transactional
    @Override
    //게시글 조회(하나)
    public RecipeResponseDTO.RecipeCreateDTO mainRecipe(Long recipeId){
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        String contentCombine = recipeRepository.findContentByRecipeId(recipeId);
        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            recipe.updateAverage(recipe, average(recipeId));
            List<String> imgUrl = imageRepository.findImageUrlsByRecipeId(recipeId);
            List<String> content = Arrays.asList(contentCombine.split("\n"));
            List<String> ingredientList = recipeRepository.findIngredientNamesByRecipeId(recipeId);
            log.info(recipeId + "번을 조회합니다.");
            return new RecipeResponseDTO.RecipeCreateDTO(recipe,imgUrl,ingredientList,content);
        }
        else{
            return null;
        }
    }
    @Override
    public String kcal(String menu){
        return null;
    }

    //사진 업로드
    public String upload(MultipartFile multipartFile) throws IOException{
        String originalFileName = multipartFile.getOriginalFilename();
        //UUID 추가
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s","_");

        //String fileName = dirName + "/" + uniqueFileName;
        log.info("fileName: " + uniqueFileName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, uniqueFileName,multipartFile.getInputStream(), metadata);
        String imgUrl = amazonS3.getUrl(bucket, uniqueFileName).toString();
        return imgUrl;
    }

    //검색 기능
//    public List<RecipeResponseDTO.RecipeCreateDTO> searchList(RecipeSearchDTO recipeSearchDTO){
//
//    }
    // 평점 계산
    public Double average(Long recipeId){
        List<Comment> commentList = commentRepository.findByrecipeId(recipeId);
        double averageScore = commentList.stream()
                .mapToInt(Comment::getScore)
                .average()
                .orElse(0.0);
        return averageScore;
    }

}
