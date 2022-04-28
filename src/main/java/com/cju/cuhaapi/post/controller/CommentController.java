package com.cju.cuhaapi.post.controller;

import com.cju.cuhaapi.post.dto.CommentResponse;
import com.cju.cuhaapi.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    @GetMapping
    public List<CommentResponse> comments(Pageable pageable) {
        return commentService.findComments(pageable).stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
}
