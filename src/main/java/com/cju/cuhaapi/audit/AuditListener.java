package com.cju.cuhaapi.audit;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditListener {

    @PrePersist
    public void setCreatedAt(Auditable auditable) {
        Audit audit = auditable.getAudit();

        if (audit == null) {
            audit = new Audit();
            auditable.setAudit(audit);
        }

        audit.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void setUpdatedAt(Auditable auditable) {
        Audit audit = auditable.getAudit();

        audit.setUpdatedAt(LocalDateTime.now());
    }
}
