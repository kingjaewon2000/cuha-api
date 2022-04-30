package com.cju.cuhaapi.challenge.domain.repository;

import com.cju.cuhaapi.challenge.domain.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.cju.cuhaapi.challenge.domain.entity.QProblem.problem;
import static com.cju.cuhaapi.challenge.domain.entity.QSolution.*;
import static com.cju.cuhaapi.member.domain.entity.QMember.*;

@RequiredArgsConstructor
public class ProblemRepositoryImpl implements ProblemQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Problem findProblem(Long problemId) {
        return queryFactory
                .selectFrom(problem)
                .join(problem.member, member).fetchJoin()
                .leftJoin(problem.solution, solution).fetchJoin()
                .where(problem.id.eq(problemId))
                .fetchOne();
    }

    @Override
    public List<Problem> findProblems(Pageable pageable, String title, ProblemType[] problemTypes, Tier[] tiers) {
        BooleanBuilder builder = new BooleanBuilder();

        if (problemTypes != null) {
            int index = 0;

            Predicate[] predicates = new Predicate[problemTypes.length];

            for (ProblemType problemType : problemTypes) {
                predicates[index] = problemTypeEq(problemType);
                index++;
            }

            builder.andAnyOf(predicates);
        }

        if (tiers != null) {
            int index = 0;

            Predicate[] predicates = new Predicate[tiers.length];

            for (Tier tier : tiers) {
                predicates[index] = tierEq(tier);
                index++;
            }

            builder.andAnyOf(predicates);
        }

        return queryFactory
                .selectFrom(problem)
                .join(problem.member, member).fetchJoin()
                .leftJoin(problem.solution, solution).fetchJoin()
                .where(titleContains(title), builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private Predicate titleContains(String title) {
        if (!StringUtils.hasText(title)) {
            return null;
        }

        return problem.title.contains(title);
    }

    private Predicate tierEq(Tier tier) {
        if (tier == null) {
            return null;
        }

        return problem.tier.eq(tier);
    }

    private Predicate problemTypeEq(ProblemType problemType) {
        if (problemType == null) {
            return null;
        }

        return problem.type.eq(problemType);
    }
}
