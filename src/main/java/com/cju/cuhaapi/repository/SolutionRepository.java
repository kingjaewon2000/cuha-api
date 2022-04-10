package com.cju.cuhaapi.repository;

import com.cju.cuhaapi.repository.entity.challenge.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
}
