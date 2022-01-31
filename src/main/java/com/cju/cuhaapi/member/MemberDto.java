package com.cju.cuhaapi.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;

        @Builder
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public String toString() {
            return "LoginRequest{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class JoinRequest {
        private String username;
        private String password;
        private String name;

        @Builder
        public JoinRequest(String username, String password, String name) {
            this.username = username;
            this.password = password;
            this.name = name;
        }

        @Override
        public String toString() {
            return "JoinRequest{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
