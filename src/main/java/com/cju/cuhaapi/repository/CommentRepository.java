package com.cju.cuhaapi.repository;

import com.cju.cuhaapi.repository.entity.post.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByPostCategoryNameAndPostIdAndId(String name, Long postId, Long commentId);
    List<Comment> findAllByPostCategoryNameAndPostId(String name, Long postId, Pageable pageable);
}