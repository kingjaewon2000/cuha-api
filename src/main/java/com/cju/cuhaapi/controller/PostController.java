package com.cju.cuhaapi.controller;

import com.cju.cuhaapi.annotation.CurrentMember;
import com.cju.cuhaapi.repository.entity.member.Member;
import com.cju.cuhaapi.controller.dto.PostDto;
import com.cju.cuhaapi.controller.dto.PostDto.PostResponse;
import com.cju.cuhaapi.controller.dto.PostDto.SaveRequest;
import com.cju.cuhaapi.repository.entity.post.Post;
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

    /**
     * 모든 게시글 조회
     */
    @ApiOperation(value = "범위 게시글 조회", notes = "범위 게시글을 조회합니다.")
    @GetMapping
    public List<PostResponse> posts(@RequestParam(defaultValue = "0") Integer start,
                                    @RequestParam(defaultValue = "100") Integer end) {
        return postService.getPosts(start, end).stream()
                .map(post -> PostResponse.of(post, postService.likeCount(post.getId())))
                .collect(Collectors.toList());
    }

    /**
     * 카테고리에 해당하는 게시글 조회
     */
    @ApiOperation(value = "범위 게시글 조회", notes = "카테고리에 해당하는 범위 게시글을 조회합니다.")
    @GetMapping("/{category}")
    public List<PostResponse> postsByCategory(@PathVariable String category,
                                              @RequestParam(defaultValue = "0") Integer start,
                                              @RequestParam(defaultValue = "100") Integer end) {
        return postService.getPosts(category, start, end).stream()
                .map(post -> PostResponse.of(post, postService.likeCount(post.getId())))
                .collect(Collectors.toList());
    }

    /**
     * id에 해당하는 게시글 조회
     */
    @ApiOperation(value = "단위 게시글 조회", notes = "게시글을 조회합니다.")
    @GetMapping("/{category}/{postId}")
    public PostResponse postById(@PathVariable Long postId,
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
}

