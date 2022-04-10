package com.cju.cuhaapi.repository.entity.challenge;

import com.cju.cuhaapi.audit.AuditListener;
import com.cju.cuhaapi.audit.Auditable;
import com.cju.cuhaapi.audit.BaseTime;
import com.cju.cuhaapi.repository.entity.member.Member;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@EntityListeners(AuditListener.class)
public class Submit implements Auditable {

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

    @Embedded
    private BaseTime baseTime;

    //== 수정 메서드 ==//
    @Override
    public void setBaseTime(BaseTime baseTime) {
        this.baseTime = baseTime;
    }

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
