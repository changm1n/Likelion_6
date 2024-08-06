package com.example.Likelion_6_BackEnd.domain.member.dto;

import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private String userEmail;

    private String password;

    private String nickname;

//    private String purpose;
//
//    private List<String> preference;

//    public static MemberDto toMemberDto(Member member) {
//        MemberDto memberDto = new MemberDto();
//
//        memberDto.setUserEmail(member.getUserEmail());
//        memberDto.setPassword(member.getPassword());
//        memberDto.setNickname(member.getNickname());
//
//        return memberDto;
//    }
    public MemberDto(Member member){
        this.userEmail = member.getUserEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
    }
}
