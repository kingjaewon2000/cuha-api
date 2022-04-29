package com.cju.cuhaapi.challenge.domain.repository;

import com.cju.cuhaapi.challenge.domain.entity.Problem;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProblemQueryRepository {

    Problem findProblem(Long problemId);

    List<Problem> findProblems(Pageable pageable);
}
