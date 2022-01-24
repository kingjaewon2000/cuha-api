package com.cju.cuhaapi.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class JoinReq {
        private String username;
        private String password;
        private String name;

        @Builder
        public JoinReq(String username, String password, String name) {
            this.username = username;
            this.password = password;
            this.name = name;
        }

        @Override
        public String toString() {
            return "JoinReq{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
