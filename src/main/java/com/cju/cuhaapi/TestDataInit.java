package com.cju.cuhaapi;

import com.cju.cuhaapi.domain.post.entity.Comment;
import com.cju.cuhaapi.domain.post.repository.CommentRepository;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.member.entity.Profile;
import com.cju.cuhaapi.domain.member.entity.Role;
import com.cju.cuhaapi.domain.member.etc.DefaultProfile;
import com.cju.cuhaapi.domain.member.etc.DefaultRole;
import com.cju.cuhaapi.domain.member.entity.Department;
import com.cju.cuhaapi.domain.member.entity.Password;
import com.cju.cuhaapi.domain.member.repository.MemberRepository;
import com.cju.cuhaapi.domain.member.repository.ProfileRepository;
import com.cju.cuhaapi.domain.member.repository.RoleRepository;
import com.cju.cuhaapi.domain.post.entity.Category;
import com.cju.cuhaapi.domain.post.entity.PostLike;
import com.cju.cuhaapi.domain.post.entity.Post;
import com.cju.cuhaapi.domain.post.repository.CategoryRepository;
import com.cju.cuhaapi.domain.post.repository.PostLikeRepository;
import com.cju.cuhaapi.domain.post.repository.PostRepository;
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
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    @PostConstruct
    public void init() {
        Role role = initRole();
        Profile profile = initProfile();
        Member member = initMember(role, profile);
        Category category = initCategory();
        Post post = initPost(member, category);

        PostLike postLike = PostLike.builder()
                .isLike(true)
                .member(member)
                .post(post)
                .build();
        postLikeRepository.save(postLike);

        Comment comment = Comment.builder()
                .body("김치볶음밥")
                .post(post)
                .member(member)
                .build();

        commentRepository.save(comment);
    }

    private Post initPost(Member member, Category category) {
        Post post = Post.builder()
                .title("제목")
                .body("제목123")
                .member(member)
                .category(category)
                .build();

        postRepository.save(post);

        return post;
    }

    private Category initCategory() {
        Category category = Category.builder()
                .name("notice")
                .description("공지사항")
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
