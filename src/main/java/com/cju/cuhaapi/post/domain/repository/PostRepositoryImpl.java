package com.cju.cuhaapi.post.domain.repository;

import com.cju.cuhaapi.member.domain.entity.QMember;
import com.cju.cuhaapi.post.domain.entity.Post;
import com.cju.cuhaapi.post.dto.PostResponse;
import com.cju.cuhaapi.post.dto.QPostResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cju.cuhaapi.member.domain.entity.QMember.member;
import static com.cju.cuhaapi.post.domain.entity.QPost.post;
import static com.cju.cuhaapi.post.domain.entity.QPostLike.postLike;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findPosts(Pageable pageable) {
        return queryFactory
                .selectFrom(post)
                .join(post.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Post> findPosts(Pageable pageable, String categoryName) {
        return queryFactory
                .selectFrom(post)
                .join(post.member, member)
                .where(post.category.name.eq(categoryName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Post findPost(String categoryName, Long postId) {
        return queryFactory
                .selectFrom(post)
                .join(post.member, member)
                .where(post.category.name.eq(categoryName).and(post.id.eq(postId)))
                .fetchOne();
    }
}
