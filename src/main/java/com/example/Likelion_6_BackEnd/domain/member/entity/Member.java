package com.example.Likelion_6_BackEnd.domain.member.entity;

import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String Userid;

    @Column(nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String nickname;

    public static Member toMember(MemberDto memberDto) {
        Member member = new Member();

        member.Userid = memberDto.getUserid();
        member.password = memberDto.getPassword();
        member.nickname = memberDto.getNickname();

        return member;
    }
}
