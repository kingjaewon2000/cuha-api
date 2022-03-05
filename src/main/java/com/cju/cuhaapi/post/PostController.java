package com.cju.cuhaapi.post;

import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.post.PostDto.CreateRequest;
import com.cju.cuhaapi.post.PostDto.GetResponse;
import com.cju.cuhaapi.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final ModelMapper modelMapper;
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
    @GetMapping("/{id}")
    public GetResponse post(@PathVariable Long id) {
        Post post = postService.getPost(id);
        GetResponse getResponse = modelMapper.map(post, GetResponse.class);

        return getResponse;
    }

    /**
     * 게시글 작성
     */
    @PostMapping
    public GetResponse create(Authentication authentication,
                              @RequestBody CreateRequest createRequest) {
        // 인증된 멤버
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Member authMember = principalDetails.getMember();

        // 게시글 작성
        Post post = modelMapper.map(createRequest, Post.class);
        post.setMember(authMember);
        Post createdPost = postService.createPost(post);

        GetResponse getResponse = modelMapper.map(createdPost, GetResponse.class);

        return getResponse;
    }

    /**
     * 게시글 수정
     */
    @PatchMapping
    public String update() {
        return "ok";
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping
    public String delete() {
        return "ok";
    }


}

