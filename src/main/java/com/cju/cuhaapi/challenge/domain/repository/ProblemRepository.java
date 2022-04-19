package com.cju.cuhaapi.challenge.domain.repository;

import com.cju.cuhaapi.challenge.domain.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
