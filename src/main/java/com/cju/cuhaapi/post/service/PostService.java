package com.cju.cuhaapi.post.service;

import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.post.domain.entity.Category;
import com.cju.cuhaapi.post.domain.entity.Post;
import com.cju.cuhaapi.post.domain.entity.PostLike;
import com.cju.cuhaapi.post.domain.repository.PostLikeRepository;
import com.cju.cuhaapi.post.domain.repository.PostRepository;
import com.cju.cuhaapi.post.dto.PostResponse;
import com.cju.cuhaapi.post.dto.PostSaveRequest;
import com.cju.cuhaapi.post.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cju.cuhaapi.member.domain.entity.Member.isEqualMember;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CategoryService categoryService;

    public List<Post> findPosts(Pageable pageable) {
        return postRepository.findPosts(pageable);
    }

    public List<Post> findPosts(Pageable pageable, String categoryName) {
        return postRepository.findPosts(pageable, categoryName);
    }

    public Post findPost(String categoryName, Long postId) {
        return postRepository.findPost(categoryName, postId);
    }

    @Transactional
    public Post savePost(String categoryName, PostSaveRequest request, Member member) {
        Category category = categoryService.findCategory(categoryName);

        Post post = Post.savePost(category, request, member);

        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(String categoryName, Long postId, PostUpdateRequest request, Member writeMember) {
        Post post = findPost(categoryName, postId);
        Member member = post.getMember();

        if (!isEqualMember(member, writeMember)) {
            throw new IllegalArgumentException("게시글을 작성한 멤버가 아닙니다.");
        }

        post.updatePost(request);

        return post;
    }

    @Transactional
    public void deletePost(String categoryName, Long id, Member writeMember) {
        Post post = findPost(categoryName, id);
        Member member = post.getMember();

        if (!isEqualMember(member, writeMember)) {
            throw new IllegalArgumentException("게시글을 작성한 멤버가 아닙니다.");
        }

        postRepository.delete(post);
    }

    @Transactional
    public void likePost(String categoryName, Long id, Member authMember) {
        Post post = findPost(categoryName, id);

        if (isLiked(categoryName, post.getId(), authMember.getId())) {
            throw new IllegalArgumentException("이미 추천하신 게시글 입니다.");
        }

        PostLike like = PostLike.createLike(post, authMember);

        postLikeRepository.save(like);
    }

    public boolean isLiked(String categoryName, Long postId, Long memberId) {
        return postLikeRepository.existsByPostCategoryNameAndPostIdAndMemberId(categoryName, postId, memberId);
    }
}
