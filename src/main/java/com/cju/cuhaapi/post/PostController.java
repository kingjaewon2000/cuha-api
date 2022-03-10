package com.cju.cuhaapi.post;

import com.cju.cuhaapi.common.BaseTime;
import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.post.PostDto.CreateRequest;
import com.cju.cuhaapi.post.PostDto.IdResponse;
import com.cju.cuhaapi.post.PostDto.PostResponse;
import com.cju.cuhaapi.post.PostDto.UpdateRequest;
import com.cju.cuhaapi.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cju.cuhaapi.mapper.PostMapper.INSTANCE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;

    /**
     * 게시글 조회
     */
    @GetMapping("/{category}")
    public List<Post> posts(@PathVariable String category) {
        return postService.findPosts(category);
    }

    /**
     * id에 해당하는 게시글 조회
     */
    @GetMapping("/{category}/{id}")
    public PostResponse post(@PathVariable Long id,
                             @PathVariable String category) {
        Post post = postService.findPost(category, id);
        PostResponse postResponse = INSTANCE.toPostResponse(post);

        return postResponse;
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/{category}")
    public PostResponse create(Authentication authentication,
                               @PathVariable String category,
                               @RequestBody CreateRequest createRequest) {
        // 인증된 멤버
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Member authMember = principalDetails.getMember();

        // 게시글 작성
        Category findCategory = categoryService.findByName(category);
        Post post = Post.builder()
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .member(authMember)
                .category(findCategory)
                .baseTime(new BaseTime())
                .build();
        Post createdPost = postService.createPost(post, category);

        PostResponse postResponse = INSTANCE.toPostResponse(createdPost);

        return postResponse;
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/{category}/{id}")
    public PostResponse update(Authentication authentication,
                               @PathVariable String category,
                               @PathVariable Long id,
                               @RequestBody UpdateRequest updateRequest) {
        // 인증된 멤버
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Member authMember = principalDetails.getMember();

        // 게시글 수정
        Post findPost = postService.findPost(category, id);

        Post post = Post.builder()
                .id(findPost.getId())
                .title(updateRequest.getTitle())
                .content(updateRequest.getContent())
                .views(findPost.getViews())
                .baseTime(findPost.getBaseTime())
                .category(findPost.getCategory())
                .member(findPost.getMember())
                .build();

        Post savedPost = postService.updatePost(post);
        PostResponse postResponse = INSTANCE.toPostResponse(savedPost);

        return postResponse;
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{category}/{id}")
    public IdResponse delete(@PathVariable String category,
                             @PathVariable Long id) {
        Post post = postService.deletePost(category, id);

        IdResponse idResponse = IdResponse.builder()
                .id(post.getId())
                .build();

        return idResponse;
    }

}

