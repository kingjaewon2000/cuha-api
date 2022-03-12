package com.cju.cuhaapi.domain.member.entity;

import com.cju.cuhaapi.utils.PasswordEncoderUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Embeddable
public class Password {
    private static PasswordEncoderUtils passwordEncoderUtils = PasswordEncoderUtils.getInstance();

    @Column
    private final int failCount = 0;

    @Column(name = "password")
    private String value;

    @Column
    private final LocalDateTime lastModifiedDate = LocalDateTime.now();

    @Builder
    public Password(String value) {
        this.value = passwordEncoderUtils.encode(value);
    }
}
