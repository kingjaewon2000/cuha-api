package com.cju.cuhaapi.member.domain.entity.etc;

import com.cju.cuhaapi.member.domain.entity.Profile;

public class DefaultProfile {
    private static final Profile profile = Profile.builder()
            .originalFilename("no-profile.gif")
            .filename("no-profile.gif")
            .build();

    private DefaultProfile() {
    }

    public static Profile getProfile() {
        return profile;
    }
}
