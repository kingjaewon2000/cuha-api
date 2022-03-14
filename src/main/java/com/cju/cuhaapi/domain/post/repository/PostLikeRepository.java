package com.cju.cuhaapi.domain.post.repository;

import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostIdAndMemberId(Long postId, Long memberId);
    Long countByPostId(Long postId);
}