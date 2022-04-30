package com.cju.cuhaapi.challenge.domain.repository;

import com.cju.cuhaapi.challenge.domain.entity.Problem;
import com.cju.cuhaapi.challenge.domain.entity.ProblemType;
import com.cju.cuhaapi.challenge.domain.entity.Tier;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProblemQueryRepository {

    Problem findProblem(Long problemId);

    List<Problem> findProblems(Pageable pageable, String title, ProblemType[] problemTypes, Tier[] tiers);
}
