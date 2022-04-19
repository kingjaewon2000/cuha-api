package com.cju.cuhaapi.post.domain.repository;

import com.cju.cuhaapi.post.domain.entity.CommentLike;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByCommentIdAndMemberId(Long commentId, Long memberId);
    List<Boolean> existsAllByMemberId(Long memberId, Pageable pageable);
    Long countByCommentId(Long commentId);
}