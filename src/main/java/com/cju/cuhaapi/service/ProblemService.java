package com.cju.cuhaapi.service;

import com.cju.cuhaapi.controller.dto.ProblemDto.CreateRequest;
import com.cju.cuhaapi.controller.dto.ProblemDto.UpdateRequest;
import com.cju.cuhaapi.repository.SubmitRepository;
import com.cju.cuhaapi.repository.entity.challenge.Problem;
import com.cju.cuhaapi.repository.ProblemRepository;
import com.cju.cuhaapi.repository.entity.challenge.Submit;
import com.cju.cuhaapi.repository.entity.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.cju.cuhaapi.repository.entity.member.Member.isEqualMember;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final SubmitRepository submitRepository;

    public Problem getProblem(Long id) {
        return problemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID값이 잘못 지정되었습니다."));
    }

    public Page<Problem> getProblems(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        return problemRepository.findAll(pageRequest);
    }

    public void createProblem(CreateRequest request, Member member) {
        Problem problem = Problem.createProblem(request, member);
        problemRepository.save(problem);
    }

    public void updateProblem(Long id, UpdateRequest request, Member authMember) {
        Problem problem = getProblem(id);
        Member member = problem.getMember();

        if (!isEqualMember(member, authMember)) {
            throw new IllegalArgumentException("문제를 수정할 수 있는 권한이 없습니다.");
        }

        problem.updateProblem(request);
        problemRepository.save(problem);
    }

    public void deleteProblem(Long id, Member authMember) {
        Problem problem = getProblem(id);

        Member member = problem.getMember();
        if (!isEqualMember(member, authMember)) {
            throw new IllegalArgumentException("문제를 수정할 수 있는 권한이 없습니다.");
        }

        problemRepository.delete(problem);
    }

    public void grading(Long id, String flag, Member authMember) {
        Problem problem = getProblem(id);
        String problemFlag = problem.getFlag();
        boolean answer = true;

        // 문제를 틀렸을때
        if (!flag.equals(problemFlag)) {
            answer = false;

            throw new IllegalArgumentException("문제를 틀렸습니다.");
        }

        Submit submit = Submit.createSubmit(answer, flag, problem, authMember);
        submitRepository.save(submit);
    }
}
