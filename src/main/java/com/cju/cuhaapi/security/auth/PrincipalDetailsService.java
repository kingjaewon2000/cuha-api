package com.cju.cuhaapi.security.auth;

import com.cju.cuhaapi.repository.entity.member.Member;
import com.cju.cuhaapi.repository.MemberRepository;
import com.cju.cuhaapi.repository.entity.member.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.cju.cuhaapi.repository.entity.member.Password.MAX_FAIL_COUNT;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException("계정을 찾을 수 없습니다." + username);
        }

        Password password = member.getPassword();
        /**
         * 로그인 시 패스워드 실패 횟수가 5회가 넘었으면 로그인 실패
         */
        int failCount = password.getFailCount();
        if (failCount >= MAX_FAIL_COUNT) {
            throw new IllegalArgumentException("패스워드 실패 횟수를 초과했습니다.");
        }

        return new PrincipalDetails(member);
    }
}
