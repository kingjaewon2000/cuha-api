package com.cju.cuhaapi.security.jwt;

public class JwtConstants {
    private JwtConstants() {
    }

    private static final long MINUTE = 60000;
    private static final long HOUR = 60 * 60000;
    private static final long DAY = 24 * HOUR;
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 1 * MINUTE;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 30 * DAY;
    public static final String JWT_SCREAT = "CUHA";
}
