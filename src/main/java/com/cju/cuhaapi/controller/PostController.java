package com.cju.cuhaapi.controller;

import com.cju.cuhaapi.annotation.CurrentMember;
import com.cju.cuhaapi.controller.dto.CommentDto;
import com.cju.cuhaapi.entity.member.Member;
import com.cju.cuhaapi.controller.dto.PostDto;
import com.cju.cuhaapi.controller.dto.PostDto.PostResponse;
import com.cju.cuhaapi.entity.post.Post;
import com.cju.cuhaapi.service.CommentService;
import com.cju.cuhaapi.service.PostService;
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
     * 모든 게시글 조회
     */
    @ApiOperation(value = "범위 게시글 조회", notes = "범위 게시글을 조회합니다.")
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
    @ApiOperation(value = "범위 게시글 조회", notes = "카테고리에 해당하는 범위 게시글을 조회합니다.")
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
    @ApiOperation(value = "단위 게시글 조회", notes = "게시글을 조회합니다.")
    @GetMapping("/{categoryName}/{postId}")
    public PostResponse postById(@PathVariable String categoryName,
                                 @PathVariable Long postId) {
        Post post = postService.getPost(categoryName, postId);

        return PostResponse.of(post, postService.likeCount(post.getId()));
    }

    /**
     * 게시글 작성
     */
    @ApiOperation(value = "게시글 생성", notes = "게시글을 생성합니다.")
    @PostMapping("/{categoryName}")
    public void save(@CurrentMember Member authMember,
                     @PathVariable String categoryName,
                     @RequestBody PostDto.SaveRequest request) {
        postService.savePost(categoryName, request, authMember);
    }

    /**
     * 게시글 수정
     */
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    @PatchMapping("/{categoryName}/{postId}")
    public void update(@CurrentMember Member authMember,
                       @PathVariable String categoryName,
                       @PathVariable Long postId,
                       @RequestBody PostDto.UpdateRequest request) {
        postService.updatePost(categoryName, postId, request, authMember);
    }

    /**
     * 게시글 삭제
     */
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    @DeleteMapping("/{categoryName}/{postId}")
    public void delete(@CurrentMember Member authMember,
                       @PathVariable String categoryName,
                       @PathVariable Long postId) {
        postService.deletePost(categoryName, postId, authMember);
    }

    @ApiOperation(value = "게시글 좋아요 확인", notes = "사용자가 게시글에 좋아요를 눌렀는지 반환합니다.")
    @GetMapping("/{categoryName}/{postId}/like")
    public boolean likePostMe(@CurrentMember Member authMember,
                              @PathVariable String categoryName,
                              @PathVariable Long postId) {
        return postService.isClickLike(categoryName, postId, authMember);
    }

    @ApiOperation(value = "게시글 좋아요", notes = "게시글을 좋아요합니다.")
    @PostMapping("/{categoryName}/{postId}/like")
    public void likePost(@CurrentMember Member authMember,
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
    @ApiOperation(value = "댓글 조회", notes = "게시글에 해당하는 모든 댓글을 조회합니다.")
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
    @ApiOperation(value = "댓글 생성", notes = "댓글을 생성합니다.")
    @PostMapping("/{categoryName}/{postId}/comments")
    public void saveComment(@CurrentMember Member authMember,
                            @PathVariable String categoryName,
                            @PathVariable Long postId,
                            @RequestBody CommentDto.SaveRequest request) {
        commentService.saveComment(categoryName, postId, request, authMember);
    }

    /**
     * category, postId에 해당하는 게시글에 commentId에 해당하는 댓글 수정
     */
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다.")
    @PatchMapping("/{categoryName}/{postId}/comments/{commentId}")
    public void updateComment(@CurrentMember Member authMember,
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
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
    @DeleteMapping("/{categoryName}/{postId}/comments/{commentId}")
    public void deleteComment(@CurrentMember Member authMember,
                              @PathVariable String categoryName,
                              @PathVariable Long postId,
                              @PathVariable Long commentId) {
        commentService.deleteComment(categoryName, postId, commentId, authMember);
    }

    @ApiOperation(value = "댓글 좋아요 확인", notes = "사용자가 댓글에 좋아요를 눌렀는지 반환합니다.")
    @GetMapping("/{categoryName}/{postId}/comments/like")
    public List<Boolean> likeCommentMe(@CurrentMember Member authMember,
                                       @PathVariable String categoryName,
                                       @PathVariable Long postId,
                                       @RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "100") Integer size) {
        return commentService.isClickLike(categoryName, postId, authMember, page, size);
    }

    @ApiOperation(value = "댓글 좋아요", notes = "댓글을 좋아요합니다.")
    @PostMapping("/{categoryName}/{postId}/comments/{commentId}/like")
    public void likeComment(@CurrentMember Member authMember,
                            @PathVariable String categoryName,
                            @PathVariable Long postId,
                            @PathVariable Long commentId) {
        commentService.likeComment(categoryName, postId, commentId, authMember);
    }
}

