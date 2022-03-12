package com.cju.cuhaapi.domain.post.repository;

import com.cju.cuhaapi.domain.post.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}