package com.cju.cuhaapi.commons.security.jwt;

import com.cju.cuhaapi.commons.security.auth.PrincipalDetails;
import com.cju.cuhaapi.member.domain.repository.MemberRepository;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.member.domain.entity.Password;
import com.cju.cuhaapi.commons.security.jwt.JwtResponseDto.Token;
import com.cju.cuhaapi.member.dto.MemberLoginRequest;
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

        MemberLoginRequest loginRequest = inputStreamToLoginRequest(request);
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

        // ????????? ????????? ???????????? ?????? ?????? ?????????
        PrincipalDetails principalDetails = ((PrincipalDetails) authResult.getPrincipal());
        Member authMember = principalDetails.getMember();
        if (authMember == null) {
            throw new UsernameNotFoundException("????????? ?????? ??? ????????????.");
        }

        Password password = authMember.getPassword();
        /**
         * ???????????? ????????? 0??? ???????????? ???????????? ?????? ?????????
         */
        if (password.getFailCount() != INIT_FAIL_COUNT) {
            password.initFailCount();
            memberRepository.save(authMember);
        }

        // Access Token ??????
        String accessToken = jwtProvider.createAccessToken(principalDetails.getMember().getId(), principalDetails.getMember().getUsername(), principalDetails.getMember().getName());

        // Refresh Token ??????
        String refreshToken = jwtProvider.createRefreshToken();

        // ??????
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
        // ????????? ??? ??? ?????? ????????? ??? ??????????
        MemberLoginRequest loginRequest = (MemberLoginRequest) request.getAttribute("loginRequest");
        String username = loginRequest.getUsername();

        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException("????????? ?????? ??? ????????????." + username);
        }

        Password password = member.getPassword();
        password.addFailCount();
        memberRepository.save(member);

        throw new BadCredentialsException("??????????????? ???????????? ????????????.");
    }

    private MemberLoginRequest inputStreamToLoginRequest(HttpServletRequest request) {
        try {
            MemberLoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), MemberLoginRequest.class);
            log.info("MemberDto.loginReq = {}", loginRequest);

            return loginRequest;
        } catch (JsonParseException e) {
            throw new IllegalArgumentException("JSON ????????? ???????????? ????????????.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("JSON ????????? ???????????? ????????????.");
    }
}
