package com.cju.cuhaapi.post.domain.repository;

import com.cju.cuhaapi.post.domain.entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostQueryRepository {

    List<Post> findPosts(Pageable pageable);
    List<Post> findPosts(Pageable pageable, String categoryName);
    Post findPost(String categoryName, Long postId);
}
