package com.cju.cuhaapi.post.domain.entity;

import com.cju.cuhaapi.commons.entity.BaseTimeEntity;
import com.cju.cuhaapi.member.domain.entity.Member;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class PostLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    //== 수정 메서드==//
    public static PostLike createLike(Post post, Member member) {
        return PostLike.builder()
                .post(post)
                .member(member)
                .build();
    }
}