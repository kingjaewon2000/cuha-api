package com.cju.cuhaapi.member.domain.entity;

import com.cju.cuhaapi.commons.entity.BaseTimeEntity;
import com.cju.cuhaapi.member.dto.MemberJoinRequest;
import com.cju.cuhaapi.member.dto.MemberUpdateInfoRequest;
import com.cju.cuhaapi.post.domain.entity.Post;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Embedded
    private Password password;

    @Column(nullable = false)
    private String name;

    @Enumerated(STRING)
    private Gender gender;

    @ColumnDefault("'example@cju.ac.kr'")
    @Column(nullable = false)
    private String email;

    @ColumnDefault("'010-0000-0000'")
    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String studentId;

    @Enumerated(STRING)
    private Department department;

    private int score;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column
    private String refreshToken;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    //== 수정자 메서드 ==//
    private void setPassword(Password password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void setGender(Gender gender) {
        this.gender = gender;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    private void setDepartment(Department department) {
        this.department = department;
    }

    private void setProfile(Profile profile) {
        this.profile = profile;
    }

    //== 생성 메서드 ==//
    public static Member join(MemberJoinRequest request, Role role, Profile profile) {
        return Member.builder()
                .username(request.getUsername())
                .password(new Password(request.getPassword()))
                .name(request.getName())
                .gender(request.getGender())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .studentId(request.getStudentId())
                .department(request.getDepartment())
                .role(role)
                .profile(profile)
                .build();
    }

    public void updateMember(MemberUpdateInfoRequest request, Profile profile) {
        if (profile != null) {
            setProfile(profile);
        }

        setName(request.getName());
        setGender(request.getGender());
        setEmail(request.getEmail());
        setPhoneNumber(request.getPhoneNumber());
        setStudentId(request.getStudentId());
        setDepartment(request.getDepartment());
    }

    //== 비지니스 메서드 ==//
    public static boolean isEqualMember(Member firstMember, Member secondMember) {
        if (firstMember.getUsername().equals(secondMember.getUsername())
                && firstMember.getId().equals(secondMember.getId())) {
            return true;
        }

        return false;
    }
}
