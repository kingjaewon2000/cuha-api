package com.cju.cuhaapi.commons;

import com.cju.cuhaapi.member.domain.entity.*;
import com.cju.cuhaapi.member.domain.repository.MemberRepository;
import com.cju.cuhaapi.member.domain.repository.ProfileRepository;
import com.cju.cuhaapi.member.domain.repository.RoleRepository;
import com.cju.cuhaapi.post.domain.entity.Category;
import com.cju.cuhaapi.post.domain.entity.Comment;
import com.cju.cuhaapi.post.domain.entity.Post;
import com.cju.cuhaapi.post.domain.entity.PostLike;
import com.cju.cuhaapi.post.domain.repository.CategoryRepository;
import com.cju.cuhaapi.post.domain.repository.CommentRepository;
import com.cju.cuhaapi.post.domain.repository.PostLikeRepository;
import com.cju.cuhaapi.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

//    @PostConstruct
    public void init() {
        Role role = initRole();
        Profile profile = initProfile();
        Member member = initMember(role, profile);
        Category category = initCategory();
        Post post = initPost(member, category);

        PostLike postLike = PostLike.builder()
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

        for (int i = 1; i <= 100; i++) {
            post = Post.builder()
                    .title("테스트 게시글" + i)
                    .body("테스트 게시글" + i)
                    .member(member)
                    .category(category)
                    .build();

            postRepository.save(post);
        }

        for (int i = 1; i <= 100; i++) {
            comment = Comment.builder()
                    .body("테스트 댓글" + i)
                    .post(post)
                    .member(member)
                    .build();

            commentRepository.save(comment);
        }

        for (int i = 1; i <= 100; i++) {
            member = Member.builder()
                    .username("member" + i)
                    .password(new Password("member" + i))
                    .name("테스트계정" + i)
                    .gender(Gender.MALE)
                    .studentId(String.valueOf(i))
                    .department(Department.DIGITAL_SECURITY)
                    .role(role)
                    .profile(profile)
                    .score((int) (Math.random() * 100000))
                    .build();

            memberRepository.save(member);
        }

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
                .gender(Gender.MALE)
                .studentId("2019010109")
                .department(Department.DIGITAL_SECURITY)
                .role(role)
                .profile(profile)
                .build();

        memberRepository.save(member);

        return member;
    }

    private Profile initProfile() {
        Profile profile = profileRepository.defaultProfile();
        profileRepository.save(profile);

        return profile;
    }

    private Role initRole() {
        Role role = roleRepository.defaultRole();
        roleRepository.save(role);

        return role;
    }
}
