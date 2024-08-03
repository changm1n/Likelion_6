package com.example.Likelion_6_BackEnd.domain.member.service;

import com.example.Likelion_6_BackEnd.domain.member.dto.LoginDto;
import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import com.example.Likelion_6_BackEnd.domain.member.entity.Member;

public interface MemberService {

    Member joinMember(MemberDto memberDto);

    Member login(LoginDto loginDto);
}
