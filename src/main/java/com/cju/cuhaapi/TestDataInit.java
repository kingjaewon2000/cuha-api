package com.cju.cuhaapi;

import com.cju.cuhaapi.member.Department;
import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.member.MemberRepository;
import com.cju.cuhaapi.member.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        String USERNAME = "root";
        String PASSWORD = "root";
        String NAME = "김태형";

        Member member = Member.builder()
                .username(USERNAME)
                .password(new Password(PASSWORD))
                .name(NAME)
                .isMale(true)
                .studentNumber("2019010109")
                .department(Department.DIGITAL_SECURITY)
                .build();

        memberRepository.save(member);
    }
}
