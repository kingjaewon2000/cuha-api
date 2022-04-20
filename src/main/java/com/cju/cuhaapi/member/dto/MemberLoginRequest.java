package com.cju.cuhaapi.member.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberLoginRequest {

    private String username;
    private String password;
}
