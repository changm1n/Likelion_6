package com.example.Likelion_6_BackEnd.domain.member.dto;

import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import lombok.Data;


@Data
public class LoginDTO {
    @Data
    public static class loginRequestDTO{
        private String userEmail;
        private String password;

    }

    @Data
    public static class loginResponseDTO{
        private String userEmail;
        private String password;
        private String nickname;

        public loginResponseDTO(Member member){
            this.userEmail = member.getUserEmail();
            this.password = member.getPassword();
            this.nickname = member.getNickname();
        }
    }


}
