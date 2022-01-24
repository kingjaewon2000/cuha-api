package com.cju.cuhaapi.member;

import lombok.Builder;
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
    private String password;

    @Column
    private LocalDateTime lastModifiedDate;

    public Password(String password) {
        this.failCount = 0;
        this.password = password;
        this.lastModifiedDate = LocalDateTime.now();
    }
}
