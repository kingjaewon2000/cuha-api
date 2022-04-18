package com.cju.cuhaapi.security.jwt;

import com.cju.cuhaapi.repository.MemberRepository;
import com.cju.cuhaapi.entity.member.Member;
import com.cju.cuhaapi.security.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.cju.cuhaapi.security.jwt.JwtConstants.TOKEN_TYPE;
import static com.cju.cuhaapi.security.jwt.JwtConstants.TOKEN_TYPE_PREFIX;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtProvider jwtProvider;
    private MemberRepository memberRepository;

    public JwtAuthorizationFilter(JwtProvider jwtProvider, AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthorizationFilter");

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith(TOKEN_TYPE)) {
            chain.doFilter(request, response);
            return ;
        }

        String token = header.replace(TOKEN_TYPE_PREFIX, "");
        String username = jwtProvider.getUsernameFromToken(token);

        if (username != null) {
            List<Member> members = memberRepository.findByUsername(username);
            if (members.size() <= 0) {
                throw new UsernameNotFoundException("계정을 찾을 수 없습니다." + username);
            }
            Member member = members.get(0);

            PrincipalDetails principalDetails = new PrincipalDetails(member);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }
    }
}
