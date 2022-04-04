package com.cju.cuhaapi.domain.challenge.repository;

import com.cju.cuhaapi.domain.challenge.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
