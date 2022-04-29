package com.cju.cuhaapi.challenge.domain.entity;

import com.cju.cuhaapi.challenge.dto.ProblemCreateRequest;
import com.cju.cuhaapi.challenge.dto.ProblemUpdateRequest;
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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "solution_id")
    private Solution solution;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //== 생성 메서드 ==//
    public static Problem createProblem(ProblemCreateRequest request, Member member) {
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

    public void updateProblem(ProblemUpdateRequest request) {
        this.type = request.getProblemType();
        this.tier = request.getTier();
        this.title = request.getTitle();
        this.body = request.getBody();
        this.score = request.getScore();
        this.flag = request.getFlag();
    }

    public void updateSolution(Solution solution) {
        this.solution = solution;
    }
}
