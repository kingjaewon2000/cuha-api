package com.cju.cuhaapi.member.dto;

import com.cju.cuhaapi.member.domain.entity.Department;
import com.cju.cuhaapi.member.domain.entity.Gender;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberInfoResponse {

    private String username;
    private String name;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String studentId;
    private Department department;
    private int score;
    private String roleDescription;
    private String profileUrl;
    private String lastModifiedDate;

    @QueryProjection
    public MemberInfoResponse(String username, String name, Gender gender, String email, String phoneNumber, String studentId, Department department, int score, String roleDescription, String profileUrl, String lastModifiedDate) {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.studentId = studentId;
        this.department = department;
        this.score = score;
        this.roleDescription = roleDescription;
        this.profileUrl = profileUrl;
        this.lastModifiedDate = lastModifiedDate;
    }
}
