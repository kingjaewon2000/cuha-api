package com.cju.cuhaapi.audit;

import org.springframework.context.annotation.Configuration;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Configuration
public class AuditListener {
    @PrePersist
    public void setCreateDate(Auditable auditable) {
        BaseTime baseTime = auditable.getBaseTime();

        if (baseTime == null) {
            baseTime = new BaseTime();
            auditable.setBaseTime(baseTime);
        }
        baseTime.setCreatedAt(LocalDateTime.now());
        baseTime.setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void setUpdateDate(Auditable auditable){
        auditable.getBaseTime().setUpdatedAt(LocalDateTime.now());
    }
}
