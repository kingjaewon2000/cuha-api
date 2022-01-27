package com.cju.cuhaapi.member;

import com.cju.cuhaapi.audit.Audit;
import com.cju.cuhaapi.audit.AuditListener;
import com.cju.cuhaapi.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditListener.class)
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

    @Column
    private String name;

    @Column
    private String refreshToken;

    @Embedded
    private Audit audit;

    @Override
    public void setAudit(Audit audit) {
        this.audit = audit;
    }
}
