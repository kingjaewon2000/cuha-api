package com.cju.cuhaapi.post.domain.entity;

import com.cju.cuhaapi.commons.entity.BaseTimeEntity;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.post.dto.CommentSaveRequest;
import com.cju.cuhaapi.post.dto.CommentUpdateRequest;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private String body;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentLike> commentLikes = new ArrayList<>();

    //== 생성 메서드 ==//
    public static Comment saveComment(CommentSaveRequest request, Post post, Member member) {
        return Comment.builder()
                .body(request.getBody())
                .post(post)
                .member(member)
                .build();
    }

    public void updateComment(CommentUpdateRequest request) {
        this.body = request.getBody();
    }
}
