package com.cju.cuhaapi.audit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class Audit {
    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
