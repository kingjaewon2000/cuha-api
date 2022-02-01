package com.cju.cuhaapi.security.jwt;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponseDto {
    private int code;
    private Token token;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Token {
        private String accessToken;
        private String refreshToken;
    }
}
