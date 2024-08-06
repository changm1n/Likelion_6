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
        List<String> ingredientNameList = getIngredientName(recipeCreateDTO.getFullRecipe());
        //레시피 content 가져오기
        List<String> recipeList = recipeCreateDTO.getContent();
        //레시피 단계 하나로 합치기
        String combineString = String.join("\n", recipeList);
        //fullRecipe 가져오기
        List<List<String>> fullRecipe = recipeCreateDTO.getFullRecipe();
        String combineFullRecipe = combineFullRecipe(fullRecipe);
        //api로 칼로리 가져오기
        String kcal = "abc";
        // 평점 가져오기
        Double average = 0.0;
        Recipe recipe = new Recipe(recipe1, combineFullRecipe ,kcal, combineString,average,user);
        recipeRepository.save(recipe);
        //재료 저장
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        for (String ingredientName : ingredientNameList/*recipeCreateDTO.getIngredient()*/){
            Ingredient ingredient = ingredientRepository.findByName(ingredientName)
                    .orElseGet(() -> {
                Ingredient newIngredient = new Ingredient(ingredientName);
                return ingredientRepository.save(newIngredient);
            });

            RecipeIngredient recipeIngredient = new RecipeIngredient(recipe, ingredient);
            recipeIngredients.add(recipeIngredient);
            recipe.getRecipeIngredientList().add(recipeIngredient);
            //ingredient.getRecipeIngredientList().add(recipeIngredient);
        }
        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            recipeIngredientRepository.save(recipeIngredient);
        }
        //사진 저장
//        List<MultipartFile> recipeImageList = recipeCreateDTO.getRecipeImages();
//        List<String> imgUrlList = new ArrayList<>();
//        for(MultipartFile imageFile : recipeImageList){
//            if(imageFile != null && !imageFile.isEmpty()){
//                String imgUrl = upload(imageFile);
//                imgUrlList.add(imgUrl);
//                Image image = new Image(imgUrl, recipe);
//                imageRepository.save(image);
//            }
//        }
        return new RecipeResponseDTO.RecipeCreateDTO(recipe/*,imgUrlList*/,fullRecipe,recipeList);
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
//            List<String> imgUrl = imageRepository.findImageUrlsByRecipeId(recipeId);
            List<String> content = Arrays.asList(contentCombine.split("\n"));
            //List<String> ingredientList = recipeRepository.findIngredientNamesByRecipeId(recipeId);
            String combineFullRecipe = recipe.getFullRecipe();
            log.info(combineFullRecipe);
            List<List<String>> fullRecipe = convertToArray(combineFullRecipe);
            System.out.println(fullRecipe);
            log.info(recipeId + "번을 조회합니다.");
            return new RecipeResponseDTO.RecipeCreateDTO(recipe/*,imgUrl*/,fullRecipe,content);
        }
        else{
            return null;
        }
    }
    //레시피 삭제
    public void delete(Long recipeId){
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            recipeRepository.delete(recipe);
        }
    }
    //홈페이지
    @Transactional
    public List<RecipeResponseDTO.RecipeCreateDTO> homePage(String userEmail){
        Optional<Member> optionalMember = memberRepository.findByuserEmail(userEmail);
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            if(member.getPurpose().isEmpty() && member.getPreference().isEmpty()){
                List<Recipe> recipeList = recipeRepository.findAll();
                List<RecipeResponseDTO.RecipeCreateDTO> List = recipeToDto(recipeList);
                return List;
            }
            String purpose = member.getPurpose();
            List<String> preference = member.getPreference();
            List<Recipe> list1 = recipeRepository.findByPurposeAndPreference(purpose, preference.get(0));
            List<Recipe> list2 = recipeRepository.findByPurposeAndPreference(purpose, preference.get(1));
            List<Recipe> list3 = recipeRepository.findByPurposeAndPreference(purpose, preference.get(2));
            List<Recipe> list = new ArrayList<>();
            list.addAll(list1);
            list.addAll(list2);
            list.addAll(list3);
            List<RecipeResponseDTO.RecipeCreateDTO> recipeList = recipeToDto(list);
            return recipeList;
        }
        else{
            return null;
        }
    }

    //마이페이지
    public List<RecipeResponseDTO.RecipeCreateDTO> myPage(String userEmail){
        Optional<Member> optionalMember = memberRepository.findByuserEmail(userEmail);
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            Long userId = member.getId();
            List<Recipe> list = recipeRepository.findByUserId(userId);
            List<RecipeResponseDTO.RecipeCreateDTO> myRecipe = recipeToDto(list);
            return myRecipe;
        }
        else{
            return null;
        }
    }

    //나의 pick 저장
    @Transactional
    public Long myPick(Long recipeId, String userEmail){
        Optional<Member> optionalMember = memberRepository.findByuserEmail(userEmail);
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            member.getMyPick().add(recipeId);
            return recipeId;
        }
        else{
            return null;
        }
    }
    //나의 pick 조회
    public List<RecipeResponseDTO.RecipeCreateDTO> myPickList(String userEmail){
        Optional<Member> optionalMember = memberRepository.findByuserEmail(userEmail);
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            List<Long> myPick = member.getMyPick();
            List<Recipe> list = new ArrayList<>();
            for(int i = 0;i < myPick.size(); i++){
                Optional<Recipe> recipeOptional = recipeRepository.findById(myPick.get(i));
                Recipe recipe = recipeOptional.get();
                list.add(recipe);
            }
            List<RecipeResponseDTO.RecipeCreateDTO> mypickList = recipeToDto(list);
            return mypickList;
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

    //검색 결과
    public List<RecipeResponseDTO.RecipeCreateDTO> searchList(RecipeSearchDTO recipeSearchDTO){
        List<String> ingredientList = recipeSearchDTO.getIngredientList();
        String purpose = recipeSearchDTO.getPurpose();
        String preference = recipeSearchDTO.getPreference();
        String cost = recipeSearchDTO.getCost();
        List<Recipe> recipeList = recipeRepository.findRecipesByCriteria(ingredientList,purpose,preference,cost);
        List<RecipeResponseDTO.RecipeCreateDTO> dtoList = recipeToDto(recipeList);
        return dtoList;
    }

    //메인페이지
//    public List<RecipeResponseDTO.RecipeCreateDTO> mainPage(String userEmail){
//        Optional<Member> memberOptional = memberRepository.findByuserEmail(userEmail);
//        if(memberOptional.isPresent()){
//            Member user = memberOptional.get();
//        }
//    }

    //레시피 리스트 dto로 변환
    public List<RecipeResponseDTO.RecipeCreateDTO> recipeToDto(List<Recipe> recipeList){
        List<RecipeResponseDTO.RecipeCreateDTO> dtoList = new ArrayList<>();
        for(Recipe recipe : recipeList){
            List<String> imgUrl = imageRepository.findImageUrlsByRecipeId(recipe.getId());
            //List<String> ingredient = recipeRepository.findIngredientNamesByRecipeId(recipe.getId());
            List<String> content = getcontent(recipe);
            List<List<String>> fullRecipe = convertToArray(recipe.getFullRecipe());
            RecipeResponseDTO.RecipeCreateDTO dto = new RecipeResponseDTO.RecipeCreateDTO(recipe/*, imgUrl*/,fullRecipe,content);
            dtoList.add(dto);
        }
        return dtoList;
    }
    // 평점 계산
    public Double average(Long recipeId){
        List<Comment> commentList = commentRepository.findByrecipeId(recipeId);
        double averageScore = commentList.stream()
                .mapToInt(Comment::getScore)
                .average()
                .orElse(0.0);
        return averageScore;
    }

    //재료 이름 가져오기
    public List<String> getIngredientName(List<List<String>> fullRecipe){
        List<String> ingredientNameList = new ArrayList<>();
        for(List<String> ingredients: fullRecipe){
            ingredientNameList.add(ingredients.get(0));
        }
        return ingredientNameList;
    }

    public List<String> getcontent(Recipe recipe){
        String combineContent = recipeRepository.findContentByRecipeId(recipe.getId());
        List<String> content = Arrays.asList(combineContent.split("\n"));
        return content;
    }

    //재료 , 개수 넘어오면 하나의 문자열로 합치기
    public String combineFullRecipe(List<List<String>> fullRecipe){
        StringJoiner outerJoiner = new StringJoiner(";");

        for(List<String> innerList: fullRecipe){
            StringJoiner innerJoiner = new StringJoiner(",");
            for(String element : innerList){
                innerJoiner.add(element);
            }
            outerJoiner.add(innerJoiner.toString());
        }

        return outerJoiner.toString();
    }
    // 문자열 다시 2차원 배열(재료, 개수)로
    public List<List<String>> convertToArray(String fullRecipe){
        List<List<String>> array = new ArrayList<>();
        String[] outerArray = fullRecipe.split(";");

        for(String outerElement : outerArray){
            String[] innerArray = outerElement.split(",");
            List<String> innerList = new ArrayList<>();
            for (String element : innerArray){
                innerList.add(element.trim());
            }
            array.add(innerList);
        }
        return array;
    }

}
