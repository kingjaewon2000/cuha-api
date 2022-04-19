package com.cju.cuhaapi.challenge.domain.entity;

import com.cju.cuhaapi.challenge.dto.ProblemDto.CreateRequest;
import com.cju.cuhaapi.challenge.dto.ProblemDto.UpdateRequest;
import com.cju.cuhaapi.commons.entity.BaseTimeEntity;
import com.cju.cuhaapi.member.domain.entity.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Problem extends BaseTimeEntity {

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

    private int score;

    @ColumnDefault("0")
    @Column(nullable = false)
    private String flag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "solution_id")
    private Solution solution;

    //== 수정 메서드 ==//
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
