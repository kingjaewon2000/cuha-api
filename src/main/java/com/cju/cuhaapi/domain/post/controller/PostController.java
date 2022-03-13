package com.cju.cuhaapi.domain.post.controller;

import com.cju.cuhaapi.domain.post.entity.Comment;
import com.cju.cuhaapi.domain.post.dto.CommentDto;
import com.cju.cuhaapi.domain.post.dto.CommentDto.CommentResponse;
import com.cju.cuhaapi.domain.post.dto.CommentDto.SaveRequest;
import com.cju.cuhaapi.domain.post.service.CommentService;
import com.cju.cuhaapi.audit.BaseTime;
import com.cju.cuhaapi.domain.post.dto.PostDto;
import com.cju.cuhaapi.domain.post.entity.Category;
import com.cju.cuhaapi.domain.post.entity.Post;
import com.cju.cuhaapi.domain.post.service.CategoryService;
import com.cju.cuhaapi.domain.post.service.PostService;
import com.cju.cuhaapi.mapper.CommentMapper;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.post.dto.PostDto.CreateRequest;
import com.cju.cuhaapi.domain.post.dto.PostDto.PostResponse;
import com.cju.cuhaapi.mapper.PostMapper;
import com.cju.cuhaapi.security.auth.PrincipalDetails;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final CategoryService categoryService;
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
                .map(PostMapper.INSTANCE::entityToPostResponse)
                .collect(Collectors.toList());
    }

    /**
     * id에 해당하는 게시글 조회
     */
    @ApiOperation(value = "단위 게시글 조회", notes = "게시글을 조회합니다.")
    @GetMapping("/{category}/{id}")
    public PostResponse post(@PathVariable Long id,
                             @PathVariable String category) {
        Post post = postService.findPost(category, id);
        PostResponse postResponse = PostMapper.INSTANCE.entityToPostResponse(post);

        return postResponse;
    }

    /**
     * 게시글 작성
     */
    @ApiOperation(value = "게시글 생성", notes = "게시글을 생성합니다.")
    @PostMapping("/{category}")
    public PostResponse create(Authentication authentication,
                               @PathVariable String category,
                               @RequestBody CreateRequest createRequest) {
        // Authentication 멤버
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Member authMember = principalDetails.getMember();

        // 게시글 작성
        Category findCategory = categoryService.findByName(category);
        Post post = PostMapper.INSTANCE.createRequestToEntity(createRequest, authMember, findCategory);
        postService.createPost(post);

        PostResponse postResponse = PostMapper.INSTANCE.entityToPostResponse(post);

        return postResponse;
    }

    /**
     * 게시글 수정
     */
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    @PatchMapping("/{category}/{id}")
    public PostResponse update(Authentication authentication,
                               @PathVariable String category,
                               @PathVariable Long id,
                               @RequestBody PostDto.UpdateRequest updateRequest) {
        // Authentication 멤버
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Member authMember = principalDetails.getMember();

        // 게시글 수정
        Post findPost = postService.findPost(category, id);
        Post post = PostMapper.INSTANCE.updateRequestToEntity(updateRequest, findPost);
        postService.updatePost(post);
        PostResponse postResponse = PostMapper.INSTANCE.entityToPostResponse(post);

        return postResponse;
    }

    /**
     * 게시글 삭제
     */
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    @DeleteMapping("/{category}/{id}")
    public PostDto.DeleteResponse delete(@PathVariable String category,
                                         @PathVariable Long id) {
        postService.deletePost(category, id);

        PostDto.DeleteResponse response =
                new PostDto.DeleteResponse(category, id);

        return response;
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
        return commentService.findComments(category, postId, start, end).stream()
                .map(CommentMapper.INSTANCE::entityToCommentResponse)
                .collect(Collectors.toList());
    }

    /**
     * category, postId에 해당하는 게시글에 댓글 생성
     */
    @ApiOperation(value = "댓글 생성", notes = "댓글을 생성합니다.")
    @PostMapping("/{category}/{postId}/comments")
    public CommentResponse saveComment(Authentication authentication,
                                       @PathVariable String category,
                                       @PathVariable Long postId,
                                       @RequestBody SaveRequest saveRequest) {
        // Authentication 멤버
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Member authMember = principalDetails.getMember();

        // 댓글 생성
        Post findPost = postService.findPost(category, postId);
        Comment comment = CommentMapper.INSTANCE.saveRequestToEntity(saveRequest, findPost, authMember);
        commentService.saveComment(comment);

        // Entity to CommentResponse
        CommentResponse response = CommentMapper.INSTANCE.entityToCommentResponse(comment);

        return response;
    }

    /**
     * category, postId에 해당하는 게시글에 commentId에 해당하는 댓글 수정
     */
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다.")
    @PatchMapping("/{category}/{postId}/comments/{commentId}")
    public CommentResponse updateComment(Authentication authentication,
                                         @PathVariable String category,
                                         @PathVariable Long postId,
                                         @PathVariable Long commentId,
                                         @RequestBody CommentDto.UpdateRequest updateRequest) {
        // Authentication 멤버
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Member authMember = principalDetails.getMember();

        // 댓글 수정
        Post findPost = postService.findPost(category, postId);
        Comment comment = CommentMapper.INSTANCE.updateRequestToEntity(updateRequest, findPost, authMember);
        commentService.saveComment(comment);

        // Entity to CommentResponse
        CommentResponse response = CommentMapper.INSTANCE.entityToCommentResponse(comment);

        return response;
    }

    /**
     * category, postId에 해당하는 게시글에 commentId에 해당하는 댓글 삭제
     */
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
    @DeleteMapping("/{category}/{postId}/comments/{commentId}")
    public CommentDto.DeleteResponse deleteComment(@PathVariable String category,
                                                   @PathVariable Long postId,
                                                   @PathVariable Long commentId) {
        commentService.deleteComment(category, postId, commentId);

        CommentDto.DeleteResponse response =
                new CommentDto.DeleteResponse(category, postId, commentId);

        return response;
    }

}

