package com.cju.cuhaapi.domain.post.repository;

import com.cju.cuhaapi.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostIdAndMemberId(Long postId, Long memberId);
    Long countByPostId(Long postId);
}