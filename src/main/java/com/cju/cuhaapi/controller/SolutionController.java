package com.cju.cuhaapi.controller;

import com.cju.cuhaapi.annotation.CurrentMember;
import com.cju.cuhaapi.controller.dto.SolutionDto.CreateRequest;
import com.cju.cuhaapi.controller.dto.SolutionDto.UpdateRequest;
import com.cju.cuhaapi.controller.dto.SolutionDto.SolutionResponse;
import com.cju.cuhaapi.entity.member.Member;
import com.cju.cuhaapi.service.SolutionService;
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
        return SolutionResponse.of(solutionService.getSolution(problemId));
    }

    @PostMapping("/{problemId}/solution")
    public void create(@CurrentMember Member member,
                       @PathVariable Long problemId,
                       @RequestBody CreateRequest request) {
        solutionService.createSolution(problemId, request, member);
    }

    @PatchMapping("/{problemId}/solution")
    public void update(@CurrentMember Member member,
                       @PathVariable Long problemId,
                       @RequestBody UpdateRequest request) {
        solutionService.updateSolution(problemId, request, member);
    }

    @DeleteMapping("/{problemId}/solution")
    public void delete(@CurrentMember Member member,
                       @PathVariable Long problemId) {
        solutionService.deleteSolution(problemId, member);
    }

}
