package com.cju.cuhaapi.member;

import com.cju.cuhaapi.audit.Audit;
import com.cju.cuhaapi.audit.AuditListener;
import com.cju.cuhaapi.audit.Auditable;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditListener.class)
@DynamicInsert
@Entity
public class Member implements Auditable {

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
    private boolean isMale;

    @ColumnDefault("'example@cju.ac.kr'")
    @Column
    private String email;

    @ColumnDefault("'010-0000-0000'")
    @Column
    private String phoneNumber;

    @Column(nullable = false)
    private String studentNumber;

    @Column(nullable = false)
    private Department department;

    @Column
    private String refreshToken;

    @Embedded
    private Audit audit;

    @Override
    public void setAudit(Audit audit) {
        this.audit = audit;
    }
}
