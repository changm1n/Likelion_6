package com.example.Likelion_6_BackEnd.domain.member.controller;

import com.example.Likelion_6_BackEnd.domain.member.dto.LoginDTO;
import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import com.example.Likelion_6_BackEnd.domain.member.dto.SurveyDTO;
import com.example.Likelion_6_BackEnd.domain.member.repository.MemberRepository;
import com.example.Likelion_6_BackEnd.domain.member.service.MemberServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class MemberController {

    private final MemberServiceImpl memberService;
    private final MemberRepository memberRepository;

    //회원가입
    @PostMapping("/save")
    public ResponseEntity<?> joinMember(@RequestBody MemberDto memberDto) {
        log.info("MemberController.save");
        log.info("memberDto = " + memberDto);
        log.info("회원가입에 성공했습니다.");
        MemberDto result = memberService.joinMember(memberDto);
        return ResponseEntity.ok().body(result);
    }
    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO.loginRequestDTO loginDTO, HttpSession session) {
        LoginDTO.loginResponseDTO loginResult = memberService.login(loginDTO);
        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getUserEmail());
            log.info(loginResult.getUserEmail()+"님이 로그인에 성공했습니다.");
            return ResponseEntity.ok().body(loginResult.getNickname());
        } else {
            return ResponseEntity.ok().body("로그인에 실패했습니다.");
        }
    }
    //설문조사 등록
    @PostMapping("/survey")
    public ResponseEntity<?> survey(@RequestBody SurveyDTO surveyDTO, HttpSession session){
        String userEmail = (String)session.getAttribute("loginEmail");
        SurveyDTO result = memberService.survey(surveyDTO, userEmail);
        log.info("운동 목적 : "+surveyDTO.getPurpose()+"과 카테고리 : "+ surveyDTO.getPreference()+"을 설정했습니다.");
        return ResponseEntity.ok().body(result);
    }
}