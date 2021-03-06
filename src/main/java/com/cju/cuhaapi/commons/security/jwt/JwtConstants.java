package com.cju.cuhaapi.commons.security.jwt;

public class JwtConstants {
    private JwtConstants() {
    }

    private static final long MINUTE = 60000;
    private static final long HOUR = 60 * 60000;
    private static final long DAY = 24 * HOUR;
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 60 * MINUTE;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 30 * DAY;
    public static final String JWT_SCREAT = "CUHA";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String TOKEN_TYPE_PREFIX = "Bearer ";
}
