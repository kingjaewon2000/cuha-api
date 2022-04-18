package com.cju.cuhaapi.repository;

import com.cju.cuhaapi.entity.challenge.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
