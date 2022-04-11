package com.cju.cuhaapi.repository;

import com.cju.cuhaapi.repository.entity.post.PostLike;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostIdAndMemberId(Long postId, Long memberId);
    boolean existsByPostCategoryNameAndPostIdAndMemberId(String name, Long postId, Long memberId);
    Long countByPostId(Long postId);
}