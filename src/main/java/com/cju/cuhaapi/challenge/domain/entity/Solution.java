package com.cju.cuhaapi.challenge.domain.entity;

import com.cju.cuhaapi.challenge.dto.SolutionCreateRequest;
import com.cju.cuhaapi.challenge.dto.SolutionUpdateRequest;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.commons.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Solution extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "solution_id")
    private Long id;

    private String body;

    @OneToOne(fetch = LAZY, mappedBy = "solution")
    private Problem problem;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //== 생성 메서드 ==//
    public static Solution createSolution(SolutionCreateRequest request, Problem problem, Member member) {
        return Solution.builder()
                .body(request.getBody())
                .problem(problem)
                .member(member)
                .build();
    }

    public void updateSolution(SolutionUpdateRequest request) {
        this.body = request.getBody();
    }
}
