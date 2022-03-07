package com.cju.cuhaapi;

import com.cju.cuhaapi.common.TimeEntity;
import com.cju.cuhaapi.member.*;
import com.cju.cuhaapi.post.PostRepository;
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
        Role role = DefaultRole.getRole();
        roleRepository.save(role);

        Profile defaultProfile = DefaultProfile.getProfile();
        profileRepository.save(defaultProfile);

        String USERNAME = "root";
        String PASSWORD = "root";
        String NAME = "김태형";

        Member member = Member.builder()
                .username(USERNAME)
                .password(new Password(PASSWORD))
                .name(NAME)
                .isMale(true)
                .studentId("2019010109")
                .department(Department.DIGITAL_SECURITY)
                .role(role)
                .profile(defaultProfile)
                .timeEntity(new TimeEntity())
                .build();

        memberRepository.save(member);
    }
}
