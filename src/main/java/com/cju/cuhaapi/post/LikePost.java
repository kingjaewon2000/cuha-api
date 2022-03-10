package com.cju.cuhaapi.post;

import com.cju.cuhaapi.common.BaseTime;
import com.cju.cuhaapi.member.Member;
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
public class LikePost {
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
}