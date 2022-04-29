package com.cju.cuhaapi.challenge.service;

import com.cju.cuhaapi.challenge.domain.repository.SubmitRepository;
import com.cju.cuhaapi.challenge.domain.entity.Problem;
import com.cju.cuhaapi.challenge.domain.repository.ProblemRepository;
import com.cju.cuhaapi.challenge.dto.ProblemCreateRequest;
import com.cju.cuhaapi.challenge.dto.ProblemUpdateRequest;
import com.cju.cuhaapi.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cju.cuhaapi.member.domain.entity.Member.isEqualMember;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final SubmitRepository submitRepository;

    public Problem findProblem(Long id) {
        return problemRepository.findProblem(id);
    }

    public List<Problem> findProblems(Pageable pageable) {
        return problemRepository.findProblems(pageable);
    }

    @Transactional
    public Problem createProblem(ProblemCreateRequest request, Member member) {
        Problem problem = Problem.createProblem(request, member);

        return problemRepository.save(problem);
    }

    @Transactional
    public void updateProblem(Long id, ProblemUpdateRequest request, Member authMember) {
        Problem problem = findProblem(id);
        Member member = problem.getMember();

        if (!isEqualMember(member, authMember)) {
            throw new IllegalArgumentException("문제를 수정할 수 있는 권한이 없습니다.");
        }

        problem.updateProblem(request);
    }

    @Transactional
    public void deleteProblem(Long id, Member authMember) {
        Problem problem = findProblem(id);

        Member member = problem.getMember();
        if (!isEqualMember(member, authMember)) {
            throw new IllegalArgumentException("문제를 수정할 수 있는 권한이 없습니다.");
        }

        problemRepository.delete(problem);
    }

//    public void grading(Long id, String flag, Member authMember) {
//        Problem problem = findProblem(id);
//        String problemFlag = problem.getFlag();
//        boolean answer = true;
//
//        // 문제를 틀렸을때
//        if (!flag.equals(problemFlag)) {
//            answer = false;
//
//            throw new IllegalArgumentException("문제를 틀렸습니다.");
//        }
//
//        Submit submit = Submit.createSubmit(answer, flag, problem, authMember);
//        submitRepository.save(submit);
//    }
}
