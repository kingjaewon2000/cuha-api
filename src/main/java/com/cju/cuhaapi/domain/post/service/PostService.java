package com.cju.cuhaapi.domain.post.service;

import com.cju.cuhaapi.domain.post.entity.Post;
import com.cju.cuhaapi.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findPosts(String category, Integer start, Integer end) {
        PageRequest pageRequest = PageRequest.of(start, end);

        return postRepository.findAllByCategoryName(category, pageRequest);
    }

    public Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID값이 잘못 지정되었습니다."));
    }

    public Post findPost(String name, Long id) {
        return postRepository.findPostByCategoryNameAndId(name, id)
                .orElseThrow(() -> new IllegalArgumentException("name값 혹은 ID값이 잘못 지정되었습니다."));
    }

    public Post createPost(Post post) {
        Post createdPost = postRepository.save(post);

        return createdPost;
    }

    public Post updatePost(Post post) {
        Post savedPost = postRepository.save(post);

        return savedPost;
    }

    public void deletePost(String name, Long id) {
        Post findPost = findPost(name, id);
        postRepository.delete(findPost);
    }
}
