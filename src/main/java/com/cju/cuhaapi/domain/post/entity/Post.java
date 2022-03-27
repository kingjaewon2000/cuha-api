package com.cju.cuhaapi.domain.post.entity;

import com.cju.cuhaapi.audit.AuditListener;
import com.cju.cuhaapi.audit.Auditable;
import com.cju.cuhaapi.audit.BaseTime;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.post.dto.PostDto.SaveRequest;
import com.cju.cuhaapi.domain.post.dto.PostDto.UpdateRequest;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@DynamicInsert
@Entity
@EntityListeners(AuditListener.class)
public class Post implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String body;

    @Column(nullable = false)
    private long views = 0L;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikes = new ArrayList<>();

    @Embedded
    private BaseTime baseTime;

    //== 수정 메서드==//
    @Override
    public void setBaseTime(BaseTime baseTime) {
        this.baseTime = baseTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //== 생성 메서드==//
    public static Post savePost(Category category, SaveRequest request, Member member) {
        return Post.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .member(member)
                .category(category)
                .build();
    }

    public static Post updatePost(UpdateRequest request, Post post) {
        post.setTitle(request.getTitle());
        post.setBody(request.getBody());

        return post;
    }
}
