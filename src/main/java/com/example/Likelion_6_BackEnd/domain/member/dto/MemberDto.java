package com.example.Likelion_6_BackEnd.domain.member.dto;

import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long id;

    private String userid;

    private String password;

    private String nickname;

    private String purpose;

    private List<String> preference;
}
