package com.cju.cuhaapi.security.auth;

import com.cju.cuhaapi.entity.member.Member;
import com.cju.cuhaapi.repository.MemberRepository;
import com.cju.cuhaapi.entity.member.Password;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static com.cju.cuhaapi.entity.member.Password.MAX_FAIL_COUNT;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Member> members = memberRepository.findByUsername(username);
        if (members.size() <= 0) {
            throw new UsernameNotFoundException("계정을 찾을 수 없습니다." + username);
        }
        Member member = members.get(0);

        Password password = member.getPassword();
        /**
         * 로그인 시 패스워드 실패 횟수가 5회가 넘었으면 로그인 실패
         */
        int failCount = password.getFailCount();
        if (failCount >= MAX_FAIL_COUNT) {
            throw new IllegalArgumentException("패스워드 실패 횟수를 초과했습니다.");
        }

        log.info("member.getClass() = {}", member.getClass());

        return new PrincipalDetails(member);
    }
}
