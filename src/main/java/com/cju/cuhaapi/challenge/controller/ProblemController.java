package com.cju.cuhaapi.challenge.controller;

import com.cju.cuhaapi.challenge.domain.entity.ProblemType;
import com.cju.cuhaapi.challenge.domain.entity.Solution;
import com.cju.cuhaapi.challenge.domain.entity.Tier;
import com.cju.cuhaapi.challenge.dto.*;
import com.cju.cuhaapi.challenge.service.SolutionService;
import com.cju.cuhaapi.commons.annotation.LoginMember;
import com.cju.cuhaapi.challenge.domain.entity.Problem;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.challenge.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/problems")
public class ProblemController {

    private final ProblemService problemService;
    private final SolutionService solutionService;

    @GetMapping
    public List<ProblemResponse> problems(Pageable pageable,
                                          @RequestParam(required = false) String title,
                                          @RequestParam(required = false) ProblemType[] problemTypes,
                                          @RequestParam(required = false) Tier[] tiers) {
        return problemService.findProblems(pageable, title, problemTypes, tiers)
                .stream()
                .map(ProblemResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{problemId}")
    public ProblemResponse problem(@PathVariable Long problemId) {
        Problem problem = problemService.findProblem(problemId);

        ProblemResponse response = new ProblemResponse(problem);

        return response;
    }

    @PostMapping
    public ProblemResponse createProblem(@LoginMember Member authMember,
                                         @RequestBody ProblemCreateRequest request) {
        Problem problem = problemService.createProblem(request, authMember);

        ProblemResponse response = new ProblemResponse(problem);

        return response;
    }

    @PatchMapping("/{problemId}")
    public void updateProblem(@LoginMember Member authMember,
                              @PathVariable Long problemId,
                              @RequestBody ProblemUpdateRequest request) {
        problemService.updateProblem(problemId, request, authMember);
    }

    @DeleteMapping("/{problemId}")
    public void deleteProblem(@LoginMember Member authMember,
                              @PathVariable Long problemId) {
        problemService.deleteProblem(problemId, authMember);
    }


//    @PostMapping("/submit/{id}")
//    public void submit(@LoginMember Member authMember,
//                       @PathVariable Long id,
//                       @RequestBody SubmitRequest request) {
//        problemService.grading(id, request.getFlag(), authMember);
//    }

    /**
     * Solution
     */

    @GetMapping("/{problemId}/solution")
    public SolutionResponse solution(@PathVariable Long problemId) {
        Solution solution = solutionService.findSolution(problemId);

        SolutionResponse response = new SolutionResponse(solution);

        return response;
    }

    @PostMapping("/{problemId}/solution")
    public void createSolution(@LoginMember Member member,
                               @PathVariable Long problemId,
                               @RequestBody SolutionCreateRequest request) {
        solutionService.createSolution(problemId, request, member);
    }

    @PatchMapping("/{problemId}/solution")
    public void updateSolution(@LoginMember Member member,
                               @PathVariable Long problemId,
                               @RequestBody SolutionUpdateRequest request) {
        solutionService.updateSolution(problemId, request, member);
    }

    @DeleteMapping("/{problemId}/solution")
    public void deleteSolution(@LoginMember Member member,
                               @PathVariable Long problemId) {
        solutionService.deleteSolution(problemId, member);
    }
}
