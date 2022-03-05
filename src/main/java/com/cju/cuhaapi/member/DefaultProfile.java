package com.cju.cuhaapi.member;

import org.springframework.stereotype.Component;

@Component
public class DefaultProfile {
    private final Profile profile;

    public DefaultProfile() {
        profile = Profile.builder()
                .originalFilename("no-profile.gif")
                .newFilename("no-profile.gif")
                .build();
    }

    public Profile getProfile() {
        return profile;
    }
}
