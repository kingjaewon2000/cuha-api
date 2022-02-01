package com.cju.cuhaapi.member;

import lombok.*;

import javax.validation.constraints.NotNull;

public class MemberDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class LoginRequest {
        @NotNull
        private String username;
        @NotNull
        private String password;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class JoinRequest {
        @NotNull
        private String username;
        @NotNull
        private String password;
        @NotNull
        private String name;

        private boolean isMale;
        private String email;
        private String phoneNumber;
        private String studentNumber;
        private String department;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class UpdateInfoRequest {
        private String name;

        private boolean isMale;
        private String email;
        private String phoneNumber;
        private String studentNumber;
        private String department;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class UpdatePasswordRequest {
        private String password;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class InfoResponse {
        private String username;
        private String name;
        private boolean isMale;
        private String email;
        private String phoneNumber;
        private String studentNumber;
        private Department department;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class IdResponse {
        private Long id;
    }
}
