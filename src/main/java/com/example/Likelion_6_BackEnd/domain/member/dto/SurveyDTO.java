package com.example.Likelion_6_BackEnd.domain.member.dto;

import com.example.Likelion_6_BackEnd.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDTO {
    private String purpose;
    private List<String> preference;

    public SurveyDTO(Member member){
        this.purpose = member.getPurpose();
        this.preference = member.getPreference();
    }
}
