package com.cju.cuhaapi.repository;

import com.cju.cuhaapi.repository.entity.post.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByCommentIdAndMemberId(Long commentId, Long memberId);
    Long countByCommentId(Long commentId);
}