package com.cju.cuhaapi.challenge.controller;

import com.cju.cuhaapi.commons.annotation.LoginMember;
import com.cju.cuhaapi.challenge.dto.ProblemDto.CreateRequest;
import com.cju.cuhaapi.challenge.dto.ProblemDto.ProblemResponse;
import com.cju.cuhaapi.challenge.dto.ProblemDto.SubmitRequest;
import com.cju.cuhaapi.challenge.dto.ProblemDto.UpdateRequest;
import com.cju.cuhaapi.challenge.domain.entity.Problem;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.challenge.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/problems")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/{id}")
    public ProblemResponse problemById(@PathVariable Long id) {
        Problem problem = problemService.getProblem(id);

        return ProblemResponse.of(problem);
    }

    @GetMapping
    public List<ProblemResponse> problems(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "100") Integer size) {
        return problemService.getProblems(page, size).stream()
                .map(problem -> ProblemResponse.of(problem))
                .collect(Collectors.toList());
    }

    @PostMapping
    public void create(@LoginMember Member authMember,
                       @RequestBody CreateRequest request) {
        problemService.createProblem(request, authMember);
    }

    @PatchMapping("/{id}")
    public void update(@LoginMember Member authMember,
                       @PathVariable Long id,
                       @RequestBody UpdateRequest request) {
        problemService.updateProblem(id, request, authMember);
    }

    @DeleteMapping("/{id}")
    public void delete(@LoginMember Member authMember,
                       @PathVariable Long id) {
        problemService.deleteProblem(id, authMember);
    }


    @PostMapping("/submit/{id}")
    public void submit(@LoginMember Member authMember,
                       @PathVariable Long id,
                       @RequestBody SubmitRequest request) {
        problemService.grading(id, request.getFlag(), authMember);
    }
}
