package com.example.Likelion_6_BackEnd.domain.member.controller;

import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import com.example.Likelion_6_BackEnd.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final  MemberService memberService;

//    @GetMapping("/user/save")
//    public String save() {
//        return "save";
//    }

    @PostMapping("/user/save")
    public ResponseEntity<?> save(@RequestBody MemberDto memberDto) {
        log.info("MemberController.save");
        log.info("memberDto = " + memberDto);
        log.info("회원가입에 성공했습니다.");
        MemberDto result = memberService.save(memberDto);
        return ResponseEntity.ok().body(result);
    }

//    @PostMapping("/user/login")
//    public String login() {
//        return "login";
//    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody MemberDto memberDto, HttpSession session) {
        MemberDto loginResult = memberService.login(memberDto);
        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getUserEmail());
            log.info(loginResult.getNickname()+"님이 로그인에 성공했습니다.");
            return ResponseEntity.ok().body(loginResult);
        } else {
            return ResponseEntity.ok().body("로그인에 실패했습니다.");
        }
    }
}