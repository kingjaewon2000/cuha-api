package com.cju.cuhaapi.domain.post.repository;

import com.cju.cuhaapi.domain.post.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByCommentIdAndMemberId(Long commentId, Long memberId);
    Long countByCommentId(Long commentId);
}