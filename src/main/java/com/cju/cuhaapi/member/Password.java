package com.cju.cuhaapi.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Embeddable
public class Password {
    @Column
    private int failCount;

    @Column
    private String value;

    @Column
    private LocalDateTime lastModifiedDate;

    public Password(String value) {
        this.failCount = 0;
        this.value = value;
        this.lastModifiedDate = LocalDateTime.now();
    }
}
