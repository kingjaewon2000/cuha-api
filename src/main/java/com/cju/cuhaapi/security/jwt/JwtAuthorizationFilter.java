package com.cju.cuhaapi.security.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cju.cuhaapi.exception.ExceptionMessage;
import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.member.MemberRepository;
import com.cju.cuhaapi.security.auth.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        if (header == null || !header.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return ;
        }

        try {
            String token = header.replace("Bearer ", "");
            String username = jwtProvider.getUsernameFromToken(token);

            if (username != null) {
                Member member = memberRepository.findByUsername(username);

                PrincipalDetails principalDetails = new PrincipalDetails(member);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            }
        } catch (TokenExpiredException e) {
            PrintWriter writer = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            ExceptionMessage tokenExpiredException = new ExceptionMessage(403, "TokenExpiredException");
            String jsonString = objectMapper.writeValueAsString(tokenExpiredException);
            writer.println(jsonString);
        }
    }
}
