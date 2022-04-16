package com.cju.cuhaapi.service;

import com.cju.cuhaapi.controller.dto.SolutionDto.CreateRequest;
import com.cju.cuhaapi.controller.dto.SolutionDto.UpdateRequest;
import com.cju.cuhaapi.repository.ProblemRepository;
import com.cju.cuhaapi.repository.SolutionRepository;
import com.cju.cuhaapi.repository.entity.challenge.Problem;
import com.cju.cuhaapi.repository.entity.challenge.Solution;
import com.cju.cuhaapi.repository.entity.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SolutionService {

    private final ProblemRepository problemRepository;
    private final ProblemService problemService;
    private final SolutionRepository solutionRepository;

    public Solution getSolution(Long problemId) {
        Problem problem = problemService.getProblem(problemId);
        Solution solution = problem.getSolution();
        if (solution == null) {
            throw new IllegalArgumentException("존재하지 않는 문제 풀이입니다.");
        }

        return solution;
    }

    public void createSolution(Long problemId, CreateRequest request, Member member) {
        Problem problem = problemService.getProblem(problemId);

        Solution solution = Solution.createSolution(request, member, problem);
        problem.updateSolution(solution);
        solutionRepository.save(solution);
        problemRepository.save(problem);
    }

    public void updateSolution(Long problemId, UpdateRequest updateRequest, Member authMember) {
        Problem problem = problemService.getProblem(problemId);
        Solution solution = problem.getSolution();

        if (solution == null) {
            throw new IllegalArgumentException("problemId에 해당하는 해결책이 없습니다.");
        }

        solution.updateSolution(updateRequest);
        solutionRepository.save(solution);
    }

    public void deleteSolution(Long problemId, Member authMember) {
        Problem problem = problemService.getProblem(problemId);
        Solution solution = problem.getSolution();

        if (solution == null) {
            throw new IllegalArgumentException("problemId에 해당하는 해결책이 없습니다.");
        }

        solutionRepository.delete(solution);
    }
}
