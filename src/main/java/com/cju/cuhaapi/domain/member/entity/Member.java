package com.cju.cuhaapi.domain.member.entity;

import com.cju.cuhaapi.audit.AuditListener;
import com.cju.cuhaapi.audit.Auditable;
import com.cju.cuhaapi.audit.BaseTime;
import com.cju.cuhaapi.domain.member.dto.MemberDto.JoinRequest;
import com.cju.cuhaapi.domain.member.dto.MemberDto.UpdateInfoRequest;
import com.cju.cuhaapi.domain.member.dto.MemberDto.UpdatePasswordRequest;
import com.cju.cuhaapi.domain.post.entity.Post;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
public class Member implements Auditable {
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

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column
    private String refreshToken;

    @Embedded
    private BaseTime baseTime;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    /**
     * setter
     */

    @Override
    public void setBaseTime(BaseTime baseTime) {
        this.baseTime = baseTime;
    }


    private void setPassword(Password password) {
        this.password = password;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setMale(Boolean male) {
        isMale = male;
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

    private void setRole(Role role) {
        this.role = role;
    }

    private void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * dto -> entity
     */

    public static Member join(JoinRequest request) {
        return Member.builder()
                .username(request.getUsername())
                .password(new Password(request.getPassword()))
                .name(request.getName())
                .isMale(request.getIsMale())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .studentId(request.getStudentId())
                .department(Department.valueOf(request.getDepartment()))
                .build();
    }

    public static Member updateInfo(UpdateInfoRequest request, Member member, Profile profile) {
        if (profile != null) {
            member.setProfile(profile);
        }

        member.setName(request.getName());
        member.setMale(request.getIsMale());
        member.setEmail(request.getEmail());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setStudentId(request.getStudentId());
        member.setDepartment(Department.valueOf(request.getDepartment()));

        return member;
    }

    public static Member updatePassword(UpdatePasswordRequest request, Member member) {
        member.setPassword(new Password(request.getPasswordAfter()));

        return member;
    }

}
