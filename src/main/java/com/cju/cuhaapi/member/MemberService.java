package com.cju.cuhaapi.member;

import com.cju.cuhaapi.member.MemberDto.JoinRequest;
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

    public void saveMember(JoinRequest joinRequest) {
        Password password = new Password(passwordEncoder.encode(joinRequest.getPassword()));
        Member member = Member.builder()
                .username(joinRequest.getUsername())
                .password(password)
                .name(joinRequest.getName())
                .build();

        memberRepository.save(member);
    }
}
