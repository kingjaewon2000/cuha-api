package com.cju.cuhaapi.domain.post.repository;

import com.cju.cuhaapi.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByCategoryName(String category);
    List<Post> findAllByCategoryName(String category, Pageable pageable);
    Optional<Post> findPostByCategoryNameAndId(String category, Long id);
}
