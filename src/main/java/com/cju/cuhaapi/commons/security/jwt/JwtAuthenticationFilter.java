package com.cju.cuhaapi.commons.security.jwt;

import com.cju.cuhaapi.commons.security.auth.PrincipalDetails;
import com.cju.cuhaapi.member.dto.MemberDto.LoginRequest;
import com.cju.cuhaapi.member.domain.repository.MemberRepository;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.member.domain.entity.Password;
import com.cju.cuhaapi.commons.security.jwt.JwtResponseDto.Token;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.cju.cuhaapi.member.domain.entity.Password.INIT_FAIL_COUNT;
import static com.cju.cuhaapi.commons.security.jwt.JwtConstants.TOKEN_TYPE_PREFIX;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;

    public JwtAuthenticationFilter(JwtProvider jwtProvider,
                                   AuthenticationManager authenticationManager,
                                   ObjectMapper objectMapper,
                                   MemberRepository memberRepository) {
        setFilterProcessesUrl("/v1/members/login");
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.memberRepository = memberRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter");

        LoginRequest loginRequest = inputStreamToLoginRequest(request);
        request.setAttribute("loginRequest", loginRequest);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("username = {}", principalDetails.getUsername());

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication");

        // 로그인 성공시 패스워드 실패 횟수 초기화
        PrincipalDetails principalDetails = ((PrincipalDetails) authResult.getPrincipal());
        Member authMember = principalDetails.getMember();
        if (authMember == null) {
            throw new UsernameNotFoundException("계정을 찾을 수 없습니다.");
        }

        Password password = authMember.getPassword();
        /**
         * 패스워드 횟수가 0이 아니라면 패스워드 횟수 초기화
         */
        if (password.getFailCount() != INIT_FAIL_COUNT) {
            password.initFailCount();
            memberRepository.save(authMember);
        }

        // Access Token 발급
        String accessToken = jwtProvider.createAccessToken(principalDetails.getMember().getId(), principalDetails.getMember().getUsername(), principalDetails.getMember().getName());

        // Refresh Token 발급
        String refreshToken = jwtProvider.createRefreshToken();

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
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 최적화 할 수 있는 방법이 더 없을까?
        LoginRequest loginRequest = (LoginRequest) request.getAttribute("loginRequest");
        String username = loginRequest.getUsername();

        List<Member> members = memberRepository.findByUsername(username);
        if (members.size() <= 0) {
            throw new UsernameNotFoundException("계정을 찾을 수 없습니다." + username);
        }
        Member findMember = members.get(0);

        Password password = findMember.getPassword();
        password.addFailCount();
        memberRepository.save(findMember);

        throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
    }

    private LoginRequest inputStreamToLoginRequest(HttpServletRequest request) {
        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            log.info("MemberDto.loginReq = {}", loginRequest);

            return loginRequest;
        } catch (JsonParseException e) {
            throw new IllegalArgumentException("JSON 형식이 옳바르지 않습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("JSON 형식이 옳바르지 않습니다.");
    }
}
