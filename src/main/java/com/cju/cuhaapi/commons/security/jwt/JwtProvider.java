package com.cju.cuhaapi.commons.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtProvider {

    public String createAccessToken(Long id, String username, String name) {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstants.ACCESS_TOKEN_EXPIRATION_TIME))
                .withClaim("id", id)
                .withClaim("username", username)
                .withClaim("name", name)
                .sign(Algorithm.HMAC512(JwtConstants.JWT_SCREAT));
    }

    public String createRefreshToken() {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JwtConstants.JWT_SCREAT));
    }

    public String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(JwtConstants.JWT_SCREAT))
                .build()
                .verify(token)
                .getClaim("username")
                .asString();
    }
}
