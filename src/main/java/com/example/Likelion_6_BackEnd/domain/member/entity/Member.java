package com.example.Likelion_6_BackEnd.domain.member.entity;

import com.example.Likelion_6_BackEnd.domain.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "member")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userid;

    @Column(nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String purpose;

    @ElementCollection
    @CollectionTable(name = "preferences", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "preference")
    private List<String> preference;
}
