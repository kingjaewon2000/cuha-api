package com.cju.cuhaapi.post.controller;

import com.cju.cuhaapi.commons.annotation.LoginMember;
import com.cju.cuhaapi.post.dto.CommentDto;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.post.dto.PostDto;
import com.cju.cuhaapi.post.dto.PostDto.PostResponse;
import com.cju.cuhaapi.post.domain.entity.Post;
import com.cju.cuhaapi.post.service.CommentService;
import com.cju.cuhaapi.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    /**
     * 모든 게시글 조회
     */
    @GetMapping
    public List<PostResponse> posts(@RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "100") Integer size) {
        return postService.getPosts(page, size).stream()
                .map(post -> PostResponse.of(post, postService.likeCount(post.getId())))
                .collect(Collectors.toList());
    }

    /**
     * 카테고리에 해당하는 게시글 조회
     */
    @GetMapping("/{categoryName}")
    public List<PostResponse> postsByCategory(@PathVariable String categoryName,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "100") Integer size) {
        return postService.getPosts(categoryName, page, size).stream()
                .map(post -> PostResponse.of(post, postService.likeCount(post.getId())))
                .collect(Collectors.toList());
    }

    /**
     * id에 해당하는 게시글 조회
     */
    @GetMapping("/{categoryName}/{postId}")
    public PostResponse postById(@PathVariable String categoryName,
                                 @PathVariable Long postId) {
        Post post = postService.getPost(categoryName, postId);

        return PostResponse.of(post, postService.likeCount(post.getId()));
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/{categoryName}")
    public void save(@LoginMember Member authMember,
                     @PathVariable String categoryName,
                     @RequestBody PostDto.SaveRequest request) {
        postService.savePost(categoryName, request, authMember);
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/{categoryName}/{postId}")
    public void update(@LoginMember Member authMember,
                       @PathVariable String categoryName,
                       @PathVariable Long postId,
                       @RequestBody PostDto.UpdateRequest request) {
        postService.updatePost(categoryName, postId, request, authMember);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{categoryName}/{postId}")
    public void delete(@LoginMember Member authMember,
                       @PathVariable String categoryName,
                       @PathVariable Long postId) {
        postService.deletePost(categoryName, postId, authMember);
    }

    @GetMapping("/{categoryName}/{postId}/like")
    public boolean likePostMe(@LoginMember Member authMember,
                              @PathVariable String categoryName,
                              @PathVariable Long postId) {
        return postService.isClickLike(categoryName, postId, authMember);
    }

    @PostMapping("/{categoryName}/{postId}/like")
    public void likePost(@LoginMember Member authMember,
                         @PathVariable String categoryName,
                         @PathVariable Long postId) {
        postService.likePost(categoryName, postId, authMember);
    }

    /**
     * 댓글
     */

    /**
     * post id에 해당하는 댓글 조회
     */
    @GetMapping("/{categoryName}/{postId}/comments")
    public List<CommentDto.CommentResponse> comments(@PathVariable String categoryName,
                                                     @PathVariable Long postId,
                                                     @RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "100") Integer size) {
        return commentService.getComments(categoryName, postId, page, size).stream()
                .map(comment ->
                        CommentDto.CommentResponse.of(comment, commentService.likeCount(comment.getId())))
                .collect(Collectors.toList());
    }

    /**
     * category, postId에 해당하는 게시글에 댓글 생성
     */
    @PostMapping("/{categoryName}/{postId}/comments")
    public void saveComment(@LoginMember Member authMember,
                            @PathVariable String categoryName,
                            @PathVariable Long postId,
                            @RequestBody CommentDto.SaveRequest request) {
        commentService.saveComment(categoryName, postId, request, authMember);
    }

    /**
     * category, postId에 해당하는 게시글에 commentId에 해당하는 댓글 수정
     */
    @PatchMapping("/{categoryName}/{postId}/comments/{commentId}")
    public void updateComment(@LoginMember Member authMember,
                              @PathVariable String categoryName,
                              @PathVariable Long postId,
                              @PathVariable Long commentId,
                              @RequestBody CommentDto.UpdateRequest request) {
        // 댓글 수정
        commentService.updateComment(categoryName, postId, commentId, request, authMember);
    }

    /**
     * category, postId에 해당하는 게시글에 commentId에 해당하는 댓글 삭제
     */
    @DeleteMapping("/{categoryName}/{postId}/comments/{commentId}")
    public void deleteComment(@LoginMember Member authMember,
                              @PathVariable String categoryName,
                              @PathVariable Long postId,
                              @PathVariable Long commentId) {
        commentService.deleteComment(categoryName, postId, commentId, authMember);
    }

    @GetMapping("/{categoryName}/{postId}/comments/like")
    public List<Boolean> likeCommentMe(@LoginMember Member authMember,
                                       @PathVariable String categoryName,
                                       @PathVariable Long postId,
                                       @RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "100") Integer size) {
        return commentService.isClickLike(categoryName, postId, authMember, page, size);
    }

    @PostMapping("/{categoryName}/{postId}/comments/{commentId}/like")
    public void likeComment(@LoginMember Member authMember,
                            @PathVariable String categoryName,
                            @PathVariable Long postId,
                            @PathVariable Long commentId) {
        commentService.likeComment(categoryName, postId, commentId, authMember);
    }
}
