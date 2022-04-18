package com.cju.cuhaapi.entity.challenge;

import com.cju.cuhaapi.entity.member.Member;
import com.cju.cuhaapi.entity.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Submit extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "submit_id")
    private Long id;

    @Column(nullable = false)
    private boolean answer;

    @Column(nullable = false)
    private String flag;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    //== 수정 메서드 ==//

    //== 생성 메서드 ==//
    public static Submit createSubmit(boolean answer, String flag, Problem problem, Member member) {
        return Submit.builder()
                .answer(answer)
                .flag(flag)
                .problem(problem)
                .member(member)
                .build();
    }
}
