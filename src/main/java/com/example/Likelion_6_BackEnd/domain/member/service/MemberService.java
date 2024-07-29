package com.example.Likelion_6_BackEnd.domain.member.service;

import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import com.example.Likelion_6_BackEnd.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDto memberDto) {
        Member member = Member.toMember(memberDto);
        memberRepository.save(member);
    }

    public MemberDto login(MemberDto memberDto) {
        Optional<Member> byUserid = memberRepository.findByUserid(memberDto.getUserid());
        if (byUserid.isPresent()) {
            Member member = byUserid.get();
            if (member.getPassword().equals(memberDto.getPassword())) {
                MemberDto dto = MemberDto.toMemberDto(member);
                return dto;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
