package com.example.Likelion_6_BackEnd.domain.member.controller;

import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import com.example.Likelion_6_BackEnd.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/user/save")
    public String save() {
        return "save";
    }

    @PostMapping("/user/save")
    public String save(@ModelAttribute MemberDto memberDto) {
        System.out.println("MemberController.save");
        System.out.println("memberDto = " + memberDto);
        memberService.save(memberDto);

        return "index";
    }

    @PostMapping("/user/login")
    public String login() {
        return "login";
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpSession session) {
        MemberDto loginResult = memberService.login(memberDto);
        if (loginResult != null) {
            session.setAttribute("loginId", loginResult.getUserid());
            return "index";
        } else {
            return "login";
        }
    }
}