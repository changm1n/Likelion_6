package com.example.Likelion_6_BackEnd.domain.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

    private String userid;
    private String password;
}
