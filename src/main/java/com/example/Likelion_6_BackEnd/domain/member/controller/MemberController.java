package com.example.Likelion_6_BackEnd.domain.member.controller;

import com.example.Likelion_6_BackEnd.domain.member.dto.LoginDto;
import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import com.example.Likelion_6_BackEnd.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<Member> joinMember(@RequestBody MemberDto memberDto) {
        Member member = memberService.joinMember(memberDto);
        return ResponseEntity.ok(member);
    }

    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody LoginDto loginDto) {
        Member member = memberService.login(loginDto);
        return ResponseEntity.ok(member);
    }
}