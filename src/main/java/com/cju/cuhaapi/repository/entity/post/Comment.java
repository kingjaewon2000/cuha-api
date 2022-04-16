package com.cju.cuhaapi.repository.entity.post;

import com.cju.cuhaapi.audit.AuditListener;
import com.cju.cuhaapi.audit.Auditable;
import com.cju.cuhaapi.audit.BaseTime;
import com.cju.cuhaapi.repository.entity.member.Member;
import com.cju.cuhaapi.controller.dto.CommentDto.SaveRequest;
import com.cju.cuhaapi.controller.dto.CommentDto.UpdateRequest;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@EntityListeners(AuditListener.class)
public class Comment implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private String body;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @Embedded
    private BaseTime baseTime;

    //== 수정 메서드 ==//
    @Override
    public void setBaseTime(BaseTime baseTime) {
        this.baseTime = baseTime;
    }

    private void setBody(String body) {
        this.body = body;
    }


    //== 생성 메서드 ==//
    public static Comment saveComment(SaveRequest request, Post post, Member member) {
        return Comment.builder()
                .body(request.getBody())
                .post(post)
                .member(member)
                .build();
    }

    public void updateComment(UpdateRequest request) {
        setBody(request.getBody());
    }
}
