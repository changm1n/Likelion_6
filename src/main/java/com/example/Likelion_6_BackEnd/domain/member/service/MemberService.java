package com.example.Likelion_6_BackEnd.domain.member.service;

import com.example.Likelion_6_BackEnd.domain.member.dto.LoginDTO;
import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import com.example.Likelion_6_BackEnd.domain.member.dto.SurveyDTO;

public interface MemberService {
    MemberDto joinMember(MemberDto memberDto);

    LoginDTO.loginResponseDTO login(LoginDTO.loginRequestDTO loginDTO);
    SurveyDTO survey(SurveyDTO surveyDTO, String userEmail);
}
