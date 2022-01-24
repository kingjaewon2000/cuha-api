package com.cju.cuhaapi.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {
    @Getter
    @NoArgsConstructor
    public static class JoinReq {
        private String username;
        private String password;

        @Builder
        public JoinReq(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
