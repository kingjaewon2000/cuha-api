package com.cju.cuhaapi.post.domain.repository;

import com.cju.cuhaapi.post.domain.entity.Post;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.cju.cuhaapi.member.domain.entity.QMember.member;
import static com.cju.cuhaapi.post.domain.entity.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findPosts(Pageable pageable) {
        Sort.Order id = pageable.getSort().getOrderFor("id");

        return queryFactory
                .selectFrom(post)
                .join(post.member, member).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderById(id))
                .fetch();
    }

    @Override
    public List<Post> findPosts(Pageable pageable, String categoryName) {
        Sort.Order id = pageable.getSort().getOrderFor("id");

        return queryFactory
                .selectFrom(post)
                .join(post.member, member).fetchJoin()
                .where(post.category.name.eq(categoryName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderById(id))
                .fetch();
    }

    @Override
    public Post findPost(String categoryName, Long postId) {
        return queryFactory
                .selectFrom(post)
                .join(post.member, member).fetchJoin()
                .where(post.category.name.eq(categoryName).and(post.id.eq(postId)))
                .fetchOne();
    }

    private OrderSpecifier<?> orderById(Sort.Order id) {
        if (id == null) {
            return post.id.asc();
        }

        Sort.Direction direction = id.getDirection();
        if (direction == null || direction.isAscending()) {
            return post.id.asc();
        }

        return post.id.desc();
    }
}
