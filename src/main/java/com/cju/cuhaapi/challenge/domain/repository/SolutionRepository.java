package com.cju.cuhaapi.challenge.domain.repository;

import com.cju.cuhaapi.challenge.domain.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SolutionRepository extends JpaRepository<Solution, Long> {

    @Query("select s from Solution s " +
            "join fetch s.problem p " +
            "join fetch s.member m " +
            "where p.id = :problemId")
    Solution findSolution(Long problemId);
}
