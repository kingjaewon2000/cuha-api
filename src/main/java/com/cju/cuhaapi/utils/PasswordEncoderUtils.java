package com.cju.cuhaapi.utils;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderUtils {
    private static PasswordEncoderUtils instance = new PasswordEncoderUtils();
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private PasswordEncoderUtils() {
    }

    public static PasswordEncoderUtils getInstance() {
        if (instance == null) {
            instance = new PasswordEncoderUtils();
        }

        return instance;
    }

    public static PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
