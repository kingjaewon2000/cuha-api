package com.cju.cuhaapi.repository.entity.challenge;

import com.cju.cuhaapi.audit.AuditListener;
import com.cju.cuhaapi.audit.Auditable;
import com.cju.cuhaapi.audit.BaseTime;
import com.cju.cuhaapi.controller.dto.ProblemDto.CreateRequest;
import com.cju.cuhaapi.controller.dto.ProblemDto.UpdateRequest;
import com.cju.cuhaapi.repository.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@EntityListeners(AuditListener.class)
public class Problem implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProblemType type;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    @Column(nullable = false)
    private String title;

    @Column
    private String body;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer score;

    @ColumnDefault("0")
    @Column(nullable = false)
    private String flag;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "solution_id")
    private Solution solution;

    @Embedded
    private BaseTime baseTime;

    //== 수정 메서드 ==//
    @Override
    public void setBaseTime(BaseTime baseTime) {
        this.baseTime = baseTime;
    }

    private void setType(ProblemType type) {
        this.type = type;
    }

    private void setTier(Tier tier) {
        this.tier = tier;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setBody(String body) {
        this.body = body;
    }

    private void setScore(Integer score) {
        this.score = score;
    }

    private void setFlag(String flag) {
        this.flag = flag;
    }

    private void setSolution(Solution solution) {
        this.solution = solution;
    }

    //== 생성 메서드 ==//
    public static Problem createProblem(CreateRequest request, Member member) {
        return Problem.builder()
                .type(request.getProblemType())
                .tier(request.getTier())
                .title(request.getTitle())
                .body(request.getBody())
                .score(request.getScore())
                .flag(request.getFlag())
                .member(member)
                .build();
    }

    public void updateProblem(UpdateRequest request) {
        setType(request.getProblemType());
        setTier(request.getTier());
        setTitle(request.getTitle());
        setBody(request.getBody());
        setScore(request.getScore());
        setFlag(request.getFlag());
    }

    public void updateSolution(Solution solution) {
        setSolution(solution);
    }
}
