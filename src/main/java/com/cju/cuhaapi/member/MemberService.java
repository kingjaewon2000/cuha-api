package com.cju.cuhaapi.member;

import com.cju.cuhaapi.member.MemberDto.JoinReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    public void saveMember(JoinReq joinReq) {
        Password password = new Password(passwordEncoder.encode(joinReq.getPassword()));
        Member member = Member.builder()
                .username(joinReq.getUsername())
                .password(password)
                .name(joinReq.getName())
                .build();

        memberRepository.save(member);
    }
}
