package com.cju.cuhaapi.controller;

import com.cju.cuhaapi.annotation.CurrentMember;
import com.cju.cuhaapi.repository.entity.member.Member;
import com.cju.cuhaapi.controller.dto.CommentDto;
import com.cju.cuhaapi.controller.dto.CommentDto.CommentResponse;
import com.cju.cuhaapi.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class CommentController {

    private final CommentService commentService;

    /**
     * 모든 댓글 조회
     */
    @ApiOperation(value = "댓글 조회", notes = "모든 댓글을 조회합니다.")
    @GetMapping("/comments")
    public List<CommentResponse> comments(@RequestParam(defaultValue = "0") Integer start,
                                          @RequestParam(defaultValue = "100") Integer end) {
        return commentService.getComments(start, end).stream()
                .map(comment ->
                        CommentResponse.of(comment, commentService.likeCount(comment.getId())))
                .collect(Collectors.toList());
    }

    /**
     * post id에 해당하는 댓글 조회
     */
    @ApiOperation(value = "댓글 조회", notes = "게시글에 해당하는 모든 댓글을 조회합니다.")
    @GetMapping("/posts/{category}/{postId}/comments")
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
    @PostMapping("/posts/{category}/{postId}/comments")
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
    @PatchMapping("/posts/{category}/{postId}/comments/{commentId}")
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
    @DeleteMapping("/posts/{category}/{postId}/comments/{commentId}")
    public void deleteComment(@CurrentMember Member authMember,
                              @PathVariable String category,
                              @PathVariable Long postId,
                              @PathVariable Long commentId) {
        commentService.deleteComment(category, postId, commentId, authMember);
    }

    @PostMapping("/posts/{category}/{postId}/comments/{commentId}/like")
    public void likeComment(@CurrentMember Member authMember,
                            @PathVariable String category,
                            @PathVariable Long postId,
                            @PathVariable Long commentId) {
        commentService.likeComment(category, postId, commentId, authMember);
    }
}
