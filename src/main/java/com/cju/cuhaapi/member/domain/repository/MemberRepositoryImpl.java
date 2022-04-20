package com.cju.cuhaapi.member.domain.repository;

import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.member.dto.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.cju.cuhaapi.member.domain.entity.QMember.member;
import static com.cju.cuhaapi.member.domain.entity.QProfile.profile;
import static com.cju.cuhaapi.member.domain.entity.QRole.role1;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findByUsername(String username) {
        return queryFactory
                .selectFrom(member)
                .leftJoin(member.role, role1).fetchJoin()
                .leftJoin(member.profile, profile).fetchJoin()
                .where(member.username.eq(username))
                .fetchOne();
    }

    @Override
    public MemberResponse findMember(String username) {
        return queryFactory
                .select(new QMemberResponse(member.username, member.name, member.score, member.profile.filename))
                .from(member)
                .join(member.profile, profile)
                .where(member.username.eq(username))
                .fetchOne();
    }

    @Override
    public MemberInfoResponse memberInfo(Long id) {
        return queryFactory
                .select(new QMemberInfoResponse(
                        member.username,
                        member.name,
                        member.gender,
                        member.email,
                        member.phoneNumber,
                        member.studentId,
                        member.department,
                        member.score,
                        member.role.description,
                        member.profile.filename,
                        member.password.lastModifiedDate.stringValue()
                        ))
                .from(member)
                .join(member.role, role1)
                .join(member.profile, profile)
                .where(member.id.eq(id))
                .fetchOne();
    }

    @Override
    public MemberRankInfoResponse memberRank(Long id) {
//        return queryFactory
//                .select(new QMemberRankInfoResponse(
//                        member.username,
//                        member.name,
//                        member.score,
//                        member.role.description,
//                        member.profile.filename,
//                        member.
//                ))
//                .from(member)
//                .join(member.role, role1)
//                .join(member.profile, profile)
//                .where(member.id.eq(id))
//                .fetchOne();

        return new MemberRankInfoResponse("test", "test", 100, "test", "test", 1);
    }

    @Override
    public List<MemberResponse> findMembers(Pageable pageable) {
        Sort.Order score = pageable.getSort().getOrderFor("score");

        List<MemberResponse> members = queryFactory
                .select(new QMemberResponse(
                        member.username,
                        member.name,
                        member.score,
                        member.profile.filename
                ))
                .from(member)
                .join(member.profile, profile)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderByScore(score))
                .fetch();

        return members;
    }

    private OrderSpecifier<?> orderByScore(Sort.Order score) {
        if (score == null) {
            throw new IllegalArgumentException("정렬 조건이 옳바르지 않습니다.");
        }

        Sort.Direction direction = score.getDirection();
        if (direction == null || direction.isAscending()) {
            return member.score.asc();
        }

        return member.score.desc();
    }
}
