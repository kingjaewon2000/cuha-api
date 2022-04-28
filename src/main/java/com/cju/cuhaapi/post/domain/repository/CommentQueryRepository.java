package com.cju.cuhaapi.post.domain.repository;

import com.cju.cuhaapi.post.domain.entity.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentQueryRepository {

    List<Comment> findComments(Pageable pageable);
    List<Comment> findComments(String categoryName, Long postId, Pageable pageable);

    Comment findComment(String categoryName, Long postId, Long commentId);
}
