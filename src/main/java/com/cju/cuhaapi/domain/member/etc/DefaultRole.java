package com.cju.cuhaapi.domain.member.etc;

import com.cju.cuhaapi.domain.member.entity.Role;

public class DefaultRole {
    private static final Role role = Role.builder()
            .role("ROLE_MEMBER")
            .build();

    private DefaultRole() {
    }

    public static Role getRole() {
        return role;
    }
}
