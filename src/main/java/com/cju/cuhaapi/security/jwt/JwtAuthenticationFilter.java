package com.cju.cuhaapi.security.jwt;

import com.cju.cuhaapi.exception.ExceptionMessage;
import com.cju.cuhaapi.member.MemberDto.LoginReq;
import com.cju.cuhaapi.security.auth.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LoginReq loginReq = objectMapper.readValue(request.getInputStream(), LoginReq.class);
            log.info("MemberDto.loginReq = {}", loginReq);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword());

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("username = {}", principalDetails.getUsername());

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication");

        // Access Token 발급
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String accessToken = jwtProvider.createAccessToken(principalDetails.getMember().getId(), principalDetails.getMember().getUsername(), principalDetails.getMember().getName());
        response.addHeader("Authorization", "Bearer " + accessToken);

        // Refresh Token 발급
        String refreshToken = jwtProvider.createRefreshToken();
        response.addHeader("REFRESH_TOKEN", refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("unsuccessfulAuthentication");

        PrintWriter writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        ExceptionMessage usernameNotFoundException = new ExceptionMessage(401, "UsernameNotFoundException");
        String jsonString = objectMapper.writeValueAsString(usernameNotFoundException);
        writer.println(jsonString);
    }
}
