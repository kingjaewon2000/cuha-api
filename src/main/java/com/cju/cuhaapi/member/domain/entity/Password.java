package com.cju.cuhaapi.member.domain.entity;

import com.cju.cuhaapi.commons.utils.PasswordEncoderUtils;
import com.cju.cuhaapi.member.dto.MemberUpdatePasswordRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Embeddable
public class Password {

    public static final int INIT_FAIL_COUNT = 0;
    public static final int MAX_FAIL_COUNT = 5;
    private static PasswordEncoderUtils passwordEncoderUtils = PasswordEncoderUtils.getInstance();

    @Column
    private int failCount;

    @Column(name = "password")
    private String value;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedDate
    private LocalDateTime lastLoginAttemptDate;

    @Builder
    public Password(String value) {
        this.value = passwordEncoderUtils.encode(value);
    }

    //== 비지니스 메서드 ==//
    public void addFailCount() {
        if (failCount >= MAX_FAIL_COUNT) {
            throw new IllegalArgumentException("패스워드 실패 횟수를 초과했습니다.");
        }

        failCount += 1;
    }

    public void initFailCount() {
        this.failCount = 0;
    }

    public void updatePassword(String value) {
        this.value = passwordEncoderUtils.encode(value);
    }
}
