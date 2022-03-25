package com.cju.cuhaapi.security.jwt;

import com.cju.cuhaapi.domain.member.dto.MemberDto;
import com.cju.cuhaapi.domain.member.dto.MemberDto.LoginRequest;
import com.cju.cuhaapi.security.auth.PrincipalDetails;
import com.cju.cuhaapi.security.jwt.JwtResponseDto.Token;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

import static com.cju.cuhaapi.security.jwt.JwtConstants.TOKEN_TYPE_PREFIX;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        setFilterProcessesUrl("/v1/members/login");
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter");

        String name = request.getParameter("name");
        log.info("{}", name);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            log.info("MemberDto.loginReq = {}", loginRequest);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("username = {}", principalDetails.getUsername());

            return authentication;
        } catch (JsonParseException e) {
            throw new IllegalArgumentException("JSON 형식이 옳바르지 않습니다.");
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
//        response.addHeader("Authorization", "Bearer " + accessToken);

        // Refresh Token 발급
        String refreshToken = jwtProvider.createRefreshToken();
//        response.addHeader("REFRESH_TOKEN", refreshToken);

        // 응답
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        PrintWriter writer = response.getWriter();
        JwtResponseDto responseDto = new JwtResponseDto(200,
                new Token(TOKEN_TYPE_PREFIX + accessToken,
                        TOKEN_TYPE_PREFIX + refreshToken));
        String responseData = objectMapper.writeValueAsString(responseDto);

        writer.print(responseData);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        throw failed;
    }
}
