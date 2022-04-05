package com.cju.cuhaapi.repository;

import com.cju.cuhaapi.repository.entity.post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostIdAndMemberId(Long postId, Long memberId);
    Long countByPostId(Long postId);
}