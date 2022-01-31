package com.cju.cuhaapi.member;

import com.cju.cuhaapi.utils.PasswordEncoderUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Embeddable
public class Password {
    private static PasswordEncoderUtils passwordEncoderUtils = PasswordEncoderUtils.getInstance();

    @Column
    private int failCount;

    @Column(name = "password")
    private String value;

    @Column
    private LocalDateTime lastModifiedDate;

    public Password(String value) {
        this.failCount = 0;
        this.value = passwordEncoderUtils.encode(value);
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void setValue(String value) {
        this.value = passwordEncoderUtils.encode(value);
    }
}
