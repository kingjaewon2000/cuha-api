package com.cju.cuhaapi.repository.entity.challenge;

import com.cju.cuhaapi.audit.AuditListener;
import com.cju.cuhaapi.audit.Auditable;
import com.cju.cuhaapi.audit.BaseTime;
import com.cju.cuhaapi.controller.dto.SolutionDto;
import com.cju.cuhaapi.controller.dto.SolutionDto.CreateRequest;
import com.cju.cuhaapi.controller.dto.SolutionDto.UpdateRequest;
import com.cju.cuhaapi.repository.entity.member.Member;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@EntityListeners(AuditListener.class)
public class Solution implements Auditable {

    @Id
    @GeneratedValue
    @Column(name = "solution_id")
    private Long id;

    private String body;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "solution")
    private Problem problem;

    @Embedded
    private BaseTime baseTime;

    //== 수정 메서드 ==//
    @Override
    public void setBaseTime(BaseTime baseTime) {
        this.baseTime = baseTime;
    }

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
