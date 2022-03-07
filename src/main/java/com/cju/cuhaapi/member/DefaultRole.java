package com.cju.cuhaapi.member;

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
