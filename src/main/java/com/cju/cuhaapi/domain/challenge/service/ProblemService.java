package com.cju.cuhaapi.domain.challenge.service;

import com.cju.cuhaapi.domain.challenge.dto.ProblemDto.CreateRequest;
import com.cju.cuhaapi.domain.challenge.dto.ProblemDto.UpdateRequest;
import com.cju.cuhaapi.domain.challenge.entity.Problem;
import com.cju.cuhaapi.domain.challenge.repository.ProblemRepository;
import com.cju.cuhaapi.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.cju.cuhaapi.domain.member.entity.Member.isSameMember;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProblemService {

    private final ProblemRepository problemRepository;

    public Problem getProblem(Long id) {
        return problemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID값이 잘못 지정되었습니다."));
    }

    public Page<Problem> getProblems(Integer start, Integer end) {
        PageRequest pageRequest = PageRequest.of(start, end, Sort.by("id").descending());
        return problemRepository.findAll(pageRequest);
    }

    public void createProblem(CreateRequest request, Member member) {
        Problem problem = Problem.createProblem(request, member);
        problemRepository.save(problem);
    }

    public void updateProblem(Long id, UpdateRequest request, Member authMember) {
        Problem problem = getProblem(id);
        Member member = problem.getMember();

        if (!isSameMember(member, authMember)) {
            throw new IllegalArgumentException("문제를 수정할 수 있는 권한이 없습니다.");
        }

        Problem updateProblem = Problem.updateProblem(request, problem);
        problemRepository.save(updateProblem);
    }

    public void deleteProblem(Long id, Member authMember) {
        Problem problem = getProblem(id);

        Member member = problem.getMember();
        if (!isSameMember(member, authMember)) {
            throw new IllegalArgumentException("문제를 수정할 수 있는 권한이 없습니다.");
        }

        problemRepository.delete(problem);
    }
}
