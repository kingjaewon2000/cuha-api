package com.cju.cuhaapi.domain.post.service;

import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.post.dto.PostDto.SaveRequest;
import com.cju.cuhaapi.domain.post.dto.PostDto.UpdateRequest;
import com.cju.cuhaapi.domain.post.entity.Category;
import com.cju.cuhaapi.domain.post.entity.Post;
import com.cju.cuhaapi.domain.post.entity.PostLike;
import com.cju.cuhaapi.domain.post.repository.PostLikeRepository;
import com.cju.cuhaapi.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CategoryService categoryService;

    public Page<Post> findPosts(Integer start, Integer end) {
        PageRequest pageRequest = PageRequest.of(start, end);

        return postRepository.findAll(pageRequest);
    }

    public List<Post> findPosts(String category, Integer start, Integer end) {
        PageRequest pageRequest = PageRequest.of(start, end);

        return postRepository.findAllByCategoryName(category, pageRequest);
    }

    public Post getPost(String name, Long id) {
        return postRepository.findPostByCategoryNameAndId(name, id)
                .orElseThrow(() -> new IllegalArgumentException("name값 혹은 ID값이 잘못 지정되었습니다."));
    }

    public void savePost(String name, SaveRequest request, Member member) {
        Category category = categoryService.getCategory(name);
        if (category == null) {
            throw new IllegalArgumentException("잘못된 카테고리 입니다.");
        }

        Post post = Post.save(category, request, member);
        postRepository.save(post);
    }

    public void updatePost(String name, Long postId, UpdateRequest request, Member member) {
        Post post = getPost(name, postId);

        if (!isCheckWriter(post.getMember(), member)) {
            throw new IllegalArgumentException("게시글을 작성한 멤버가 아닙니다.");
        }

        postRepository.save(Post.update(request, post));
    }

    public void deletePost(String name, Long id, Member member) {
        Post post = getPost(name, id);

        if (!isCheckWriter(post.getMember(), member)) {
            throw new IllegalArgumentException("게시글을 작성한 멤버가 아닙니다.");
        }

        postRepository.delete(post);
    }

    public void likePost(String name, Long id, Member member) {
        Post post = getPost(name, id);

        if (postLikeRepository.existsByPostIdAndMemberId(post.getId(), member.getId())) {
            throw new IllegalArgumentException("이미 추천하신 게시글 입니다.");
        }

        PostLike like = PostLike.builder()
                .member(member)
                .post(post)
                .build();

        postLikeRepository.save(like);
    }

    public Long likeCount(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

    private boolean isCheckWriter(Member writer, Member member) {
        if (writer.getUsername().equals(member.getUsername())
                && writer.getId().equals(member.getId())) {
            return true;
        }

        return false;
    }
}
