package com.example.Likelion_6_BackEnd.domain.member.service;

import com.example.Likelion_6_BackEnd.domain.member.dto.LoginDto;
import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import com.example.Likelion_6_BackEnd.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member joinMember(MemberDto memberDto) {
        if (memberDto.getUserid() == null || memberDto.getPassword() == null || memberDto.getNickname() == null) {
            throw new IllegalArgumentException("필수 필드 누락");
        }

        if (memberDto.getPurpose() == null || memberDto.getPurpose().isEmpty()) {
            throw new IllegalArgumentException("purpose must not be null or empty.");
        }

        Member member = new Member();
        member.setUserid(memberDto.getUserid());
        member.setPassword(memberDto.getPassword());
        member.setNickname(memberDto.getNickname());
        member.setPurpose(memberDto.getPurpose());
        member.setPreference(memberDto.getPreference());

        return memberRepository.save(member);
    }

    @Override
    public Member login(LoginDto loginDto) {
        Optional<Member> optionalMember = memberRepository.findByUserid(loginDto.getUserid());
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getPassword().equals(loginDto.getPassword())) {
                return member;
            } else {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }
    }
}