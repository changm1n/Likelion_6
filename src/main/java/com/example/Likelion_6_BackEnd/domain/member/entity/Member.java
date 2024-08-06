package com.example.Likelion_6_BackEnd.domain.member.entity;

import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(unique = true, nullable = false)
    private String userEmail;

    //@Column(nullable = false)
    private String password;

    //@Column(length = 50, nullable = false)
    private String nickname;

    private String purpose;

    @ElementCollection
    private List<Long> myPick;

    @ElementCollection
    @CollectionTable(name = "preferences", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "preference")
    private List<String> preference;

    protected Member(){}
    public Member(String userEmail, String password, String nickname){
        this.userEmail = userEmail;
        this.password = password;
        this.nickname = nickname;
    }

    public void putSurvey(Member member, String purpose, List<String> preference){
        this.userEmail = member.getUserEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.purpose = purpose;
        this.preference = preference;
    }
}
