package com.cju.cuhaapi;

import com.cju.cuhaapi.common.BaseTime;
import com.cju.cuhaapi.member.*;
import com.cju.cuhaapi.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @PostConstruct
    public void init() {
        Role role = initRole();
        Profile profile = initProfile();
        Member member = initMember(role, profile);
        Category category = initCategory();
        Post post = initPost(member, category);

        LikePost likePost = LikePost.builder()
                .isLike(true)
                .member(member)
                .post(post)
                .baseTime(new BaseTime())
                .build();
        likeRepository.save(likePost);
    }

    private Post initPost(Member member, Category category) {
        Post post = Post.builder()
                .title("제목")
                .content("제목123")
                .member(member)
                .category(category)
                .baseTime(new BaseTime())
                .build();

        postRepository.save(post);

        return post;
    }

    private Category initCategory() {
        Category category = Category.builder()
                .name("/notice")
                .description("공지사항")
                .baseTime(new BaseTime())
                .build();

        categoryRepository.save(category);

        return category;
    }

    private Member initMember(Role role, Profile profile) {
        Member member = Member.builder()
                .username("root")
                .password(new Password("root"))
                .name("김태형")
                .isMale(true)
                .studentId("2019010109")
                .department(Department.DIGITAL_SECURITY)
                .role(role)
                .profile(profile)
                .baseTime(new BaseTime())
                .build();

        memberRepository.save(member);

        return member;
    }

    private Profile initProfile() {
        Profile profile = DefaultProfile.getProfile();
        profileRepository.save(profile);

        return profile;
    }

    private Role initRole() {
        Role role = DefaultRole.getRole();
        roleRepository.save(role);

        return role;
    }
}
