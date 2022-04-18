package com.cju.cuhaapi.controller;

import com.cju.cuhaapi.annotation.CurrentMember;
import com.cju.cuhaapi.controller.dto.ProblemDto.CreateRequest;
import com.cju.cuhaapi.controller.dto.ProblemDto.ProblemResponse;
import com.cju.cuhaapi.controller.dto.ProblemDto.SubmitRequest;
import com.cju.cuhaapi.controller.dto.ProblemDto.UpdateRequest;
import com.cju.cuhaapi.entity.challenge.Problem;
import com.cju.cuhaapi.entity.member.Member;
import com.cju.cuhaapi.service.ProblemService;
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
    public void create(@CurrentMember Member authMember,
                       @RequestBody CreateRequest request) {
        problemService.createProblem(request, authMember);
    }

    @PatchMapping("/{id}")
    public void update(@CurrentMember Member authMember,
                       @PathVariable Long id,
                       @RequestBody UpdateRequest request) {
        problemService.updateProblem(id, request, authMember);
    }

    @DeleteMapping("/{id}")
    public void delete(@CurrentMember Member authMember,
                       @PathVariable Long id) {
        problemService.deleteProblem(id, authMember);
    }


    @PostMapping("/submit/{id}")
    public void submit(@CurrentMember Member authMember,
                       @PathVariable Long id,
                       @RequestBody SubmitRequest request) {
        problemService.grading(id, request.getFlag(), authMember);
    }
}
