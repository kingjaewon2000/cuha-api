package com.cju.cuhaapi.challenge.controller;

import com.cju.cuhaapi.challenge.dto.ProblemCreateRequest;
import com.cju.cuhaapi.challenge.dto.ProblemResponse;
import com.cju.cuhaapi.challenge.dto.ProblemUpdateRequest;
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

    @GetMapping("/{problemId}")
    public ProblemResponse problem(@PathVariable Long problemId) {
        Problem problem = problemService.findProblem(problemId);

        ProblemResponse response = new ProblemResponse(problem);

        return response;
    }

    @GetMapping
    public List<ProblemResponse> problems(Pageable pageable) {
        return problemService.findProblems(pageable)
                .stream()
                .map(ProblemResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ProblemResponse create(@LoginMember Member authMember,
                       @RequestBody ProblemCreateRequest request) {
        Problem problem = problemService.createProblem(request, authMember);

        ProblemResponse response = new ProblemResponse(problem);

        return response;
    }

    @PatchMapping("/{problemId}")
    public void update(@LoginMember Member authMember,
                       @PathVariable Long problemId,
                       @RequestBody ProblemUpdateRequest request) {
        problemService.updateProblem(problemId, request, authMember);
    }

    @DeleteMapping("/{problemId}")
    public void delete(@LoginMember Member authMember,
                       @PathVariable Long problemId) {
        problemService.deleteProblem(problemId, authMember);
    }


//    @PostMapping("/submit/{id}")
//    public void submit(@LoginMember Member authMember,
//                       @PathVariable Long id,
//                       @RequestBody SubmitRequest request) {
//        problemService.grading(id, request.getFlag(), authMember);
//    }
}
