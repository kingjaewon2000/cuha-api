package com.cju.cuhaapi.service;

import com.cju.cuhaapi.repository.entity.member.Member;
import com.cju.cuhaapi.controller.dto.PostDto.SaveRequest;
import com.cju.cuhaapi.controller.dto.PostDto.UpdateRequest;
import com.cju.cuhaapi.repository.entity.post.Category;
import com.cju.cuhaapi.repository.entity.post.Post;
import com.cju.cuhaapi.repository.entity.post.PostLike;
import com.cju.cuhaapi.repository.PostLikeRepository;
import com.cju.cuhaapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cju.cuhaapi.repository.entity.member.Member.isEqualMember;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CategoryService categoryService;

    public Page<Post> getPosts(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());

        return postRepository.findAll(pageRequest);
    }

    public List<Post> getPosts(String categoryName, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());

        return postRepository.findAllByCategoryName(categoryName, pageRequest);
    }

    public Post getPost(String categoryName, Long id) {
        return postRepository.findPostByCategoryNameAndId(categoryName, id)
                .orElseThrow(() -> new IllegalArgumentException("name값 혹은 ID값이 잘못 지정되었습니다."));
    }

    public void savePost(String categoryName, SaveRequest request, Member member) {
        Category category = categoryService.getCategory(categoryName);
        if (category == null) {
            throw new IllegalArgumentException("잘못된 카테고리 입니다.");
        }

        Post post = Post.savePost(category, request, member);
        postRepository.save(post);
    }

    public void updatePost(String categoryName, Long postId, UpdateRequest request, Member writeMember) {
        Post post = getPost(categoryName, postId);
        Member member = post.getMember();

        if (!isEqualMember(member, writeMember)) {
            throw new IllegalArgumentException("게시글을 작성한 멤버가 아닙니다.");
        }

        post.updatePost(request);
        postRepository.save(post);
    }

    public void deletePost(String categoryName, Long id, Member writeMember) {
        Post post = getPost(categoryName, id);
        Member member = post.getMember();

        if (!isEqualMember(member, writeMember)) {
            throw new IllegalArgumentException("게시글을 작성한 멤버가 아닙니다.");
        }

        postRepository.delete(post);
    }

    public void likePost(String categoryName, Long id, Member authMember) {
        Post post = getPost(categoryName, id);

        if (postLikeRepository.existsByPostIdAndMemberId(post.getId(), authMember.getId())) {
            throw new IllegalArgumentException("이미 추천하신 게시글 입니다.");
        }

        PostLike like = PostLike.builder()
                .member(authMember)
                .post(post)
                .build();

        postLikeRepository.save(like);
    }

    public Long likeCount(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

    public boolean isClickLike(String categoryName, Long postId, Member authMember) {
        return postLikeRepository.existsByPostCategoryNameAndPostIdAndMemberId(categoryName, postId, authMember.getId());
    }
}
