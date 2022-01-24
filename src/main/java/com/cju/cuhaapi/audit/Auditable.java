package com.cju.cuhaapi.audit;

public interface Auditable {

    Audit getAudit();
    void setAudit(Audit audit);
}
