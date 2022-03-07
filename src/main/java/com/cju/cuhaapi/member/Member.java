package com.cju.cuhaapi.member;

import com.cju.cuhaapi.common.TimeEntity;
import com.cju.cuhaapi.member.MemberDto.IdResponse;
import com.cju.cuhaapi.member.MemberDto.InfoResponse;
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
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Embedded
    private Password password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isMale;

    @ColumnDefault("'example@cju.ac.kr'")
    @Column
    private String email;

    @ColumnDefault("'010-0000-0000'")
    @Column
    private String phoneNumber;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column
    private String refreshToken;

    @Embedded
    private TimeEntity timeEntity;
}
