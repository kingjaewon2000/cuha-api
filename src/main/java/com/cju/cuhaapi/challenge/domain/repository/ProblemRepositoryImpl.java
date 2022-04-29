package com.cju.cuhaapi.challenge.domain.repository;

import com.cju.cuhaapi.challenge.domain.entity.Problem;
import com.cju.cuhaapi.challenge.domain.entity.QProblem;
import com.cju.cuhaapi.challenge.domain.entity.QSolution;
import com.cju.cuhaapi.member.domain.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

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
                .join(problem.member, member)
                .join(problem.solution, solution)
                .where(problem.id.eq(problemId))
                .fetchOne();
    }

    @Override
    public List<Problem> findProblems(Pageable pageable) {
        return queryFactory
                .selectFrom(problem)
                .join(problem.member, member)
                .join(problem.solution, solution)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
