package com.cju.cuhaapi.domain.post.entity;

import com.cju.cuhaapi.audit.AuditListener;
import com.cju.cuhaapi.audit.Auditable;
import com.cju.cuhaapi.audit.BaseTime;
import com.cju.cuhaapi.domain.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@DynamicInsert
@Entity
@EntityListeners(AuditListener.class)
public class PostLike implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_post_id")
    private Long id;

    @ColumnDefault("false")
    @Column
    private Boolean isLike;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Embedded
    private BaseTime baseTime;

    @Override
    public void setBaseTime(BaseTime baseTime) {
        this.baseTime = baseTime;
    }
}