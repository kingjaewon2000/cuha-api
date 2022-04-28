package com.cju.cuhaapi.post.controller;

import com.cju.cuhaapi.commons.annotation.LoginMember;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.post.domain.entity.Post;
import com.cju.cuhaapi.post.dto.*;
import com.cju.cuhaapi.post.service.CommentService;
import com.cju.cuhaapi.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    public List<PostResponse> posts(Pageable pageable) {
        return postService.findPosts(pageable)
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리에 해당하는 게시글 조회
     */
    @GetMapping("/{categoryName}")
    public List<PostResponse> postsByCategoryName(Pageable pageable,
                                                  @PathVariable String categoryName) {
        return postService.findPosts(pageable, categoryName)
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * id에 해당하는 게시글 조회
     */
    @GetMapping("/{categoryName}/{postId}")
    public PostResponse postById(@PathVariable String categoryName,
                                 @PathVariable Long postId) {
        return new PostResponse(postService.findPost(categoryName, postId));
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/{categoryName}")
    public PostResponse save(@LoginMember Member authMember,
                             @PathVariable String categoryName,
                             @RequestBody PostSaveRequest request) {
        Post post = postService.savePost(categoryName, request, authMember);

        PostResponse response = new PostResponse(post);

        return response;
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/{categoryName}/{postId}")
    public PostResponse update(@LoginMember Member authMember,
                       @PathVariable String categoryName,
                       @PathVariable Long postId,
                       @RequestBody PostUpdateRequest request) {
        Post post = postService.updatePost(categoryName, postId, request, authMember);

        PostResponse response = new PostResponse(post);

        return response;
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

    /**
     * 게시글 좋아요 여부 확인
     */
    @GetMapping("/{categoryName}/{postId}/like")
    public boolean isLiked(@LoginMember Member authMember,
                           @PathVariable String categoryName,
                           @PathVariable Long postId) {
        return postService.isLiked(categoryName, postId, authMember.getId());
    }

    /**
     * 게시글 좋아요!
     */
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
    public List<CommentResponse> comments(@PathVariable String categoryName,
                                          @PathVariable Long postId,
                                          Pageable pageable) {
        return commentService.findComments(categoryName, postId, pageable).stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * category, postId에 해당하는 게시글에 댓글 생성
     */
    @PostMapping("/{categoryName}/{postId}/comments")
    public void saveComment(@LoginMember Member authMember,
                            @PathVariable String categoryName,
                            @PathVariable Long postId,
                            @RequestBody CommentSaveRequest request) {
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
                              @RequestBody CommentUpdateRequest request) {
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

//    @GetMapping("/{categoryName}/{postId}/comments/like")
//    public List<Boolean> likeCommentMe(@LoginMember Member authMember,
//                                       @PathVariable String categoryName,
//                                       @PathVariable Long postId,
//                                       Pageable pageable) {
//        return commentService.isLiked(categoryName, postId, authMember, pageable);
//    }

    @PostMapping("/{categoryName}/{postId}/comments/{commentId}/like")
    public void likeComment(@LoginMember Member authMember,
                            @PathVariable String categoryName,
                            @PathVariable Long postId,
                            @PathVariable Long commentId) {
        commentService.likeComment(categoryName, postId, commentId, authMember);
    }
}

