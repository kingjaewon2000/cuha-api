package com.cju.cuhaapi.challenge.service;

import com.cju.cuhaapi.challenge.domain.repository.ProblemRepository;
import com.cju.cuhaapi.challenge.domain.repository.SolutionRepository;
import com.cju.cuhaapi.challenge.domain.entity.Problem;
import com.cju.cuhaapi.challenge.domain.entity.Solution;
import com.cju.cuhaapi.challenge.dto.SolutionCreateRequest;
import com.cju.cuhaapi.challenge.dto.SolutionUpdateRequest;
import com.cju.cuhaapi.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SolutionService {

    private final ProblemService problemService;
    private final SolutionRepository solutionRepository;

    public Solution findSolution(Long problemId) {
        Solution solution = solutionRepository.findSolution(problemId);

        if (solution == null) {
            throw new IllegalArgumentException("존재하지 않는 문제 풀이입니다.");
        }

        return solution;
    }

    @Transactional
    public Solution createSolution(Long problemId, SolutionCreateRequest request, Member member) {
        Problem problem = problemService.findProblem(problemId);

        Solution solution = Solution.createSolution(request, problem, member);
        problem.updateSolution(solution);

        return solutionRepository.save(solution);
    }

    @Transactional
    public void updateSolution(Long problemId, SolutionUpdateRequest updateRequest, Member authMember) {
        Solution solution = findSolution(problemId);
        solution.updateSolution(updateRequest);
    }

    @Transactional
    public void deleteSolution(Long problemId, Member authMember) {
        Solution solution = findSolution(problemId);
        solutionRepository.delete(solution);
    }
}
