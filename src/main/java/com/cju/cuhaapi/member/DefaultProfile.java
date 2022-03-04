package com.cju.cuhaapi.member;

import org.springframework.stereotype.Component;

@Component
public class DefaultProfile {
    private final ProfileRepository profileRepository;
    private Profile profile;

    public DefaultProfile(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        profile = Profile.builder()
                .originalFilename("no-profile.gif")
                .newFilename("no-profile.gif")
                .build();

        profileRepository.save(profile);
    }

    public Profile getProfile() {
        return profile;
    }
}
