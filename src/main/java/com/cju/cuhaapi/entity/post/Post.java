package com.cju.cuhaapi.entity.post;

import com.cju.cuhaapi.controller.dto.PostDto.SaveRequest;
import com.cju.cuhaapi.controller.dto.PostDto.UpdateRequest;
import com.cju.cuhaapi.entity.member.Member;
import com.cju.cuhaapi.entity.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String body;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Long views;

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

    //== 수정 메서드==//
    private void setTitle(String title) {
        this.title = title;
    }

    private void setBody(String body) {
        this.body = body;
    }

    private void setCategory(Category category) {
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

    public void updatePost(UpdateRequest request) {
        setTitle(request.getTitle());
        setBody(request.getBody());
    }
}
