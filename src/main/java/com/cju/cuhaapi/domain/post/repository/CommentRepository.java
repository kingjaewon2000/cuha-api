package com.cju.cuhaapi.domain.post.repository;

import com.cju.cuhaapi.domain.post.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByPostCategoryNameAndPostIdAndId(String name, Long postId, Long commentId);
    List<Comment> findAllByPostCategoryNameAndPostId(String name, Long postId, Pageable pageable);
}