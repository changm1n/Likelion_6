package com.example.Likelion_6_BackEnd.domain.member.dto;

import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDto {

    private String Userid;

    private String password;

    private String nickname;

    public static MemberDto toMemberDto(Member member) {
        MemberDto memberDto = new MemberDto();

        memberDto.setUserid(memberDto.getUserid());
        memberDto.setPassword(member.getPassword());
        memberDto.setNickname(member.getNickname());

        return memberDto;
    }
}
