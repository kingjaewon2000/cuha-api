package com.cju.cuhaapi.member.dto;

import com.cju.cuhaapi.member.domain.entity.Department;
import com.cju.cuhaapi.member.domain.entity.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberUpdateInfoRequest {

    private String name;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String studentId;
    private Department department;
}
