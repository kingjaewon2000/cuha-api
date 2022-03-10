package com.cju.cuhaapi.comment;

import com.cju.cuhaapi.common.BaseTime;
import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.post.Post;
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
public class LikeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_comment_id")
    private Long id;

    @ColumnDefault("false")
    @Column
    private Boolean isLike;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Embedded
    private BaseTime baseTime;
}
