package com.cju.cuhaapi.post.domain.repository;

import com.cju.cuhaapi.post.domain.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.cju.cuhaapi.member.domain.entity.QMember.member;
import static com.cju.cuhaapi.post.domain.entity.QComment.comment;
import static com.cju.cuhaapi.post.domain.entity.QPost.post;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findComments(Pageable pageable) {
        return queryFactory
                .selectFrom(comment)
                .join(comment.member, member).fetchJoin()
                .join(comment.post, post).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Comment> findComments(String categoryName, Long postId, Pageable pageable) {
        return queryFactory
                .selectFrom(comment)
                .join(comment.member, member).fetchJoin()
                .join(comment.post, post).fetchJoin()
                .where(comment.post.category.name.eq(categoryName)
                        .and(comment.post.id.eq(postId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Comment findComment(String categoryName, Long postId, Long commentId) {
        return queryFactory
                .selectFrom(comment)
                .join(comment.member, member).fetchJoin()
                .join(comment.post, post).fetchJoin()
                .where(comment.post.category.name.eq(categoryName)
                        .and(comment.post.id.eq(postId))
                        .and(comment.id.eq(commentId)))
                .fetchOne();
    }
}
