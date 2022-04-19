package com.cju.cuhaapi.challenge.domain.entity;

import com.cju.cuhaapi.challenge.dto.SolutionDto.CreateRequest;
import com.cju.cuhaapi.challenge.dto.SolutionDto.UpdateRequest;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, mappedBy = "solution")
    private Problem problem;

    //== 수정 메서드 ==//
    private void setBody(String body) {
        this.body = body;
    }

    //== 비지니스 메서드 ==//


    //== 생성 메서드 ==//
    public static Solution createSolution(CreateRequest request, Member member, Problem problem) {
        return Solution.builder()
                .body(request.getBody())
                .member(member)
                .problem(problem)
                .build();
    }

    public void updateSolution(UpdateRequest request) {
        setBody(request.getBody());
    }
}
