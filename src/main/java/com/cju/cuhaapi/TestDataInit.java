package com.cju.cuhaapi;

import com.cju.cuhaapi.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;

    @PostConstruct
    public void init() {
        String USERNAME = "root";
        String PASSWORD = "root";
        String NAME = "김태형";

        Role role = Role.builder()
                .role("ROLE_MEMBER")
                .build();

        roleRepository.save(role);

        Profile profile = Profile.builder()
                .originalFilename("no-profile.gif")
                .newFilename("no-profile.gif")
                .build();

        profileRepository.save(profile);

        Member member = Member.builder()
                .username(USERNAME)
                .password(new Password(PASSWORD))
                .name(NAME)
                .isMale(true)
                .studentNumber("2019010109")
                .department(Department.DIGITAL_SECURITY)
                .role(role)
                .profile(profile)
                .build();

        memberRepository.save(member);
    }
}
