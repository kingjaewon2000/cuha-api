package com.cju.cuhaapi.post;

import com.cju.cuhaapi.common.BaseTime;
import com.cju.cuhaapi.member.Member;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Entity
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column(nullable = false)
    private long views = 0L;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private BaseTime baseTime = new BaseTime();
}
