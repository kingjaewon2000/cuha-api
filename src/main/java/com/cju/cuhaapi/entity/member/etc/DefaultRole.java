package com.cju.cuhaapi.entity.member.etc;

import com.cju.cuhaapi.entity.member.Role;

public class DefaultRole {
    private static final Role role = Role.builder()
            .role("ROLE_MEMBER")
            .description("동아리원")
            .build();

    private DefaultRole() {
    }

    public static Role getRole() {
        return role;
    }
}
