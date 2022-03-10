package com.cju.cuhaapi.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findPosts(String category) {
        return postRepository.findAllByCategoryName(category);
    }

    public Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID값이 잘못 지정되었습니다."));
    }

    public Post findPost(String name, Long id) {
        return postRepository.findPostByCategoryNameAndId(name, id)
                .orElseThrow(() -> new IllegalArgumentException("name값 혹은 ID값이 잘못 지정되었습니다."));
    }

    public Post createPost(Post post, String name) {
//        post = PostMapper.INSTANCE.createRequestToEntity();
        Post createdPost = postRepository.save(post);

        return createdPost;
    }

    public Post updatePost(Post post) {
        Post savedPost = postRepository.save(post);

        return savedPost;
    }

    public Post deletePost(String name, Long id) {
        Post findPost = findPost(name, id);
        postRepository.delete(findPost);

        return findPost;
    }
}
