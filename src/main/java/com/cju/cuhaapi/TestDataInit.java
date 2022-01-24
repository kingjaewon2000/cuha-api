package com.cju.cuhaapi;

import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.member.MemberRepository;
import com.cju.cuhaapi.member.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        String USERNAME = "root";
        String PASSWORD = "root";
        String NAME = "김태형";

        String encodedPassword = passwordEncoder.encode(PASSWORD);

        Member member = Member.builder()
                .username(USERNAME)
                .password(new Password(encodedPassword))
                .name(NAME)
                .build();

        memberRepository.save(member);
    }
}
