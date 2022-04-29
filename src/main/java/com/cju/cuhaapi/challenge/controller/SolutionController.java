package com.cju.cuhaapi.challenge.controller;

import com.cju.cuhaapi.challenge.domain.entity.Solution;
import com.cju.cuhaapi.challenge.dto.SolutionCreateRequest;
import com.cju.cuhaapi.challenge.dto.SolutionResponse;
import com.cju.cuhaapi.challenge.dto.SolutionUpdateRequest;
import com.cju.cuhaapi.commons.annotation.LoginMember;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.challenge.service.SolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/problems")
public class SolutionController {

    private final SolutionService solutionService;

    @GetMapping("/{problemId}/solution")
    public SolutionResponse solution(@PathVariable Long problemId) {
        Solution solution = solutionService.findSolution(problemId);

        SolutionResponse response = new SolutionResponse(solution);

        return response;
    }

    @PostMapping("/{problemId}/solution")
    public void create(@LoginMember Member member,
                       @PathVariable Long problemId,
                       @RequestBody SolutionCreateRequest request) {
        solutionService.createSolution(problemId, request, member);
    }

    @PatchMapping("/{problemId}/solution")
    public void update(@LoginMember Member member,
                       @PathVariable Long problemId,
                       @RequestBody SolutionUpdateRequest request) {
        solutionService.updateSolution(problemId, request, member);
    }

    @DeleteMapping("/{problemId}/solution")
    public void delete(@LoginMember Member member,
                       @PathVariable Long problemId) {
        solutionService.deleteSolution(problemId, member);
    }

}
