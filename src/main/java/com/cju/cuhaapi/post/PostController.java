package com.cju.cuhaapi.post;

import com.cju.cuhaapi.mapper.PostMapper;
import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.post.PostDto.CreateRequest;
import com.cju.cuhaapi.post.PostDto.PostResponse;
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

    /**
     * 게시글 조회
     */
    @GetMapping
    public List<Post> posts() {
        return postService.getPosts();
    }

    /**
     * id에 해당하는 게시글 조회
     */
    @GetMapping("/{category}/{id}")
    public PostResponse post(@PathVariable Long id) {
        Post post = postService.getPost(id);
        PostResponse postResponse = INSTANCE.toPostResponse(post);

        return postResponse;
    }

    /**
     * 게시글 작성
     */
    @PostMapping
    public PostResponse create(Authentication authentication,
                               @RequestBody CreateRequest createRequest) {
        // 인증된 멤버
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Member authMember = principalDetails.getMember();

        // 게시글 작성
        Post post = INSTANCE.createRequestToEntity(createRequest, authMember);
        Post createdPost = postService.createPost(post);

        PostResponse postResponse = INSTANCE.toPostResponse(post);

        return postResponse;
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/{id}")
    public String update() {
        return "ok";
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    public String delete() {
        return "ok";
    }


}

