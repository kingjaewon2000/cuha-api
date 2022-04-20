package com.cju.cuhaapi.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberUpdatePasswordRequest {

    private String passwordBefore;
    private String passwordAfter;
}
