package com.cju.cuhaapi.comment;

import com.cju.cuhaapi.common.BaseTime;
import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.post.Post;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Embedded
    private BaseTime baseTime;
}
