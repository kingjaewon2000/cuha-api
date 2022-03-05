package com.cju.cuhaapi;

import com.cju.cuhaapi.common.TimeEntity;
import com.cju.cuhaapi.member.*;
import com.cju.cuhaapi.post.Post;
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
    private final DefaultProfile defaultProfile;
    private final PostRepository postRepository;

    @PostConstruct
    public void init() {
        String USERNAME = "root";
        String PASSWORD = "root";
        String NAME = "김태형";

        Role role = Role.builder()
                .role("ROLE_MEMBER")
                .build();

        roleRepository.save(role);

        Profile defaultProfile = this.defaultProfile.getProfile();
        profileRepository.save(defaultProfile);

        Member member = Member.builder()
                .username(USERNAME)
                .password(new Password(PASSWORD))
                .name(NAME)
                .isMale(true)
                .studentNumber("2019010109")
                .department(Department.DIGITAL_SECURITY)
                .role(role)
                .profile(defaultProfile)
                .timeEntity(new TimeEntity())
                .build();

        memberRepository.save(member);

        Post post = Post.builder()
                .title("test")
                .content("test")
                .member(member)
                .timeEntity(new TimeEntity())
                .build();

        postRepository.save(post);
    }
}
