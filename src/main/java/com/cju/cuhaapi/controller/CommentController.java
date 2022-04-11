package com.cju.cuhaapi.controller;

import com.cju.cuhaapi.controller.dto.CommentDto.CommentResponse;
import com.cju.cuhaapi.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * 모든 댓글 조회
     */
    @ApiOperation(value = "댓글 조회", notes = "모든 댓글을 조회합니다.")
    @GetMapping
    public List<CommentResponse> comments(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "100") Integer size) {
        return commentService.getComments(page, size).stream()
                .map(comment ->
                        CommentResponse.of(comment, commentService.likeCount(comment.getId())))
                .collect(Collectors.toList());
    }
}
