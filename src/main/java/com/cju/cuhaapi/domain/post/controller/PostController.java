package com.cju.cuhaapi.domain.post.controller;

import com.cju.cuhaapi.annotation.CurrentMember;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.post.dto.CommentDto;
import com.cju.cuhaapi.domain.post.dto.CommentDto.CommentResponse;
import com.cju.cuhaapi.domain.post.dto.PostDto;
import com.cju.cuhaapi.domain.post.dto.PostDto.SaveRequest;
import com.cju.cuhaapi.domain.post.dto.PostDto.PostResponse;
import com.cju.cuhaapi.domain.post.entity.Comment;
import com.cju.cuhaapi.domain.post.entity.Post;
import com.cju.cuhaapi.domain.post.service.CommentService;
import com.cju.cuhaapi.domain.post.service.PostService;
import io.swagger.annotations.ApiOperation;
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
     * 게시글 조회
     */
    @ApiOperation(value = "범위 게시글 조회", notes = "모든 게시글을 조회합니다.")
    @GetMapping("/{category}")
    public List<PostResponse> posts(@PathVariable String category,
                                    @RequestParam(defaultValue = "0") Integer start,
                                    @RequestParam(defaultValue = "100") Integer end) {
        return postService.findPosts(category, start, end).stream()
                .map(post -> PostResponse.of(post, postService.likeCount(post.getId())))
                .collect(Collectors.toList());
    }

    /**
     * id에 해당하는 게시글 조회
     */
    @ApiOperation(value = "단위 게시글 조회", notes = "게시글을 조회합니다.")
    @GetMapping("/{category}/{postId}")
    public PostResponse post(@PathVariable Long postId,
                             @PathVariable String category) {
        Post post = postService.getPost(category, postId);

        return PostResponse.of(post, postService.likeCount(post.getId()));
    }

    /**
     * 게시글 작성
     */
    @ApiOperation(value = "게시글 생성", notes = "게시글을 생성합니다.")
    @PostMapping("/{category}")
    public void save(@CurrentMember Member authMember,
                     @PathVariable String category,
                     @RequestBody SaveRequest request) {
        postService.savePost(category, request, authMember);
    }

    /**
     * 게시글 수정
     */
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    @PatchMapping("/{category}/{postId}")
    public void update(@CurrentMember Member authMember,
                       @PathVariable String category,
                       @PathVariable Long postId,
                       @RequestBody PostDto.UpdateRequest request) {
        postService.updatePost(category, postId, request, authMember);
    }

    /**
     * 게시글 삭제
     */
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    @DeleteMapping("/{category}/{postId}")
    public void delete(@CurrentMember Member authMember,
                       @PathVariable String category,
                       @PathVariable Long postId) {
        postService.deletePost(category, postId, authMember);
    }

    @PostMapping("/{category}/{postId}")
    public void likePost(@CurrentMember Member authMember,
                         @PathVariable String category,
                         @PathVariable Long postId) {
        postService.likePost(category, postId, authMember);
    }

    /**
     * 댓글 CRUD
     */

    /**
     * post id에 해당하는 댓글 조회
     */
    @ApiOperation(value = "댓글 조회", notes = "게시글에 해당하는 모든 댓글을 조회합니다.")
    @GetMapping("/{category}/{postId}/comments")
    public List<CommentResponse> comments(@PathVariable String category,
                                          @PathVariable Long postId,
                                          @RequestParam(defaultValue = "0") Integer start,
                                          @RequestParam(defaultValue = "100") Integer end) {
        return commentService.getComments(category, postId, start, end).stream()
                .map(comment ->
                        CommentResponse.of(comment, commentService.likeCount(comment.getId())))
                .collect(Collectors.toList());
    }

    /**
     * category, postId에 해당하는 게시글에 댓글 생성
     */
    @ApiOperation(value = "댓글 생성", notes = "댓글을 생성합니다.")
    @PostMapping("/{category}/{postId}/comments")
    public void saveComment(@CurrentMember Member authMember,
                            @PathVariable String category,
                            @PathVariable Long postId,
                            @RequestBody CommentDto.SaveRequest request) {
        commentService.saveComment(category, postId, request, authMember);
    }

    /**
     * category, postId에 해당하는 게시글에 commentId에 해당하는 댓글 수정
     */
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다.")
    @PatchMapping("/{category}/{postId}/comments/{commentId}")
    public void updateComment(@CurrentMember Member authMember,
                              @PathVariable String category,
                              @PathVariable Long postId,
                              @PathVariable Long commentId,
                              @RequestBody CommentDto.UpdateRequest request) {
        // 댓글 수정
        commentService.updateComment(category, postId, commentId, request, authMember);
    }

    /**
     * category, postId에 해당하는 게시글에 commentId에 해당하는 댓글 삭제
     */
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
    @DeleteMapping("/{category}/{postId}/comments/{commentId}")
    public void deleteComment(@CurrentMember Member authMember,
                              @PathVariable String category,
                              @PathVariable Long postId,
                              @PathVariable Long commentId) {
        commentService.deleteComment(category, postId, commentId, authMember);
    }

    @PostMapping("/{category}/{postId}/comments/{commentId}/like")
    public void likeComment(@CurrentMember Member authMember,
                            @PathVariable String category,
                            @PathVariable Long postId,
                            @PathVariable Long commentId) {
        commentService.likeComment(category, postId, commentId, authMember);
    }
}

