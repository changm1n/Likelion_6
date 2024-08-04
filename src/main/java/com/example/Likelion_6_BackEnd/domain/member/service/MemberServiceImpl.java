package com.example.Likelion_6_BackEnd.domain.member.service;

import com.example.Likelion_6_BackEnd.domain.member.dto.LoginDTO;
import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import com.example.Likelion_6_BackEnd.domain.member.dto.SurveyDTO;
import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import com.example.Likelion_6_BackEnd.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberDto joinMember(MemberDto memberDto) {
        if (memberDto.getUserEmail() == null || memberDto.getPassword() == null || memberDto.getNickname() == null) {
            throw new IllegalArgumentException("필수 필드 누락");
        }

//        if (memberDto.getPurpose() == null || memberDto.getPurpose().isEmpty()) {
//            throw new IllegalArgumentException("purpose must not be null or empty.");
//        }

//        Member member = new Member();
//        member.setUserEmail(memberDto.getUserEmail());
//        member.setPassword(memberDto.getPassword());
//        member.setNickname(memberDto.getNickname());
        Member member = new Member(memberDto.getUserEmail(),memberDto.getPassword(),memberDto.getNickname());
//        member.setPurpose(memberDto.getPurpose());
//        member.setPreference(memberDto.getPreference());
        memberRepository.save(member);
        return new MemberDto(member);
    }

    @Override
    @Transactional
    public LoginDTO.loginResponseDTO login(LoginDTO.loginRequestDTO loginDto) {
        Optional<Member> optionalMember = memberRepository.findByuserEmail(loginDto.getUserEmail());
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getPassword().equals(loginDto.getPassword())) {
                return new LoginDTO.loginResponseDTO(member);
            } else {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }
    }

    @Override
    @Transactional
    public SurveyDTO survey(SurveyDTO surveyDTO, String userEmail){
        Optional<Member> memberOptional = memberRepository.findByuserEmail(userEmail);
        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            member.putSurvey(member, surveyDTO.getPurpose(), surveyDTO.getPreference());
            return new SurveyDTO(member);
        }
        else{
            return null;
        }
    }

}
