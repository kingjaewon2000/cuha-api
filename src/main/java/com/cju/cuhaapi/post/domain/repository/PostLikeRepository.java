package com.cju.cuhaapi.post.domain.repository;

import com.cju.cuhaapi.post.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostCategoryNameAndPostIdAndMemberId(String name, Long postId, Long memberId);
}