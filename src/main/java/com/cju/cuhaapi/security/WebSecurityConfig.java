package com.cju.cuhaapi.security;

import com.cju.cuhaapi.domain.member.repository.MemberRepository;
import com.cju.cuhaapi.security.jwt.*;
import com.cju.cuhaapi.utils.PasswordEncoderUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    private final JwtExceptionHandlerFilter filter;

    @Bean
    public JwtProvider getJwtProvider() {
        return new JwtProvider();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderUtils.getInstance().getPasswordEncoder();
    }

//    @Bean
//    public JwtAuthenticationFilter getJwtAuthenticationFilter() throws Exception {
//        JwtAuthenticationFilter jwtAuthenticationFilter =
//                new JwtAuthenticationFilter(getJwtProvider(), objectMapper);
//        jwtAuthenticationFilter.setFilterProcessesUrl("/v1/members/login");
//        return jwtAuthenticationFilter;
//    }
//
//    @Bean
//    public JwtAuthorizationFilter getJwtAuthorizationFilter() throws Exception {
//        JwtAuthorizationFilter jwtAuthorizationFilter =
//                new JwtAuthorizationFilter(getJwtProvider(), memberRepository);
//        return jwtAuthorizationFilter;
//    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, e) -> {
            throw e;
        });
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());

        http
                .addFilter(new JwtAuthenticationFilter(getJwtProvider(), authenticationManager(), objectMapper))
                .addFilter(new JwtAuthorizationFilter(getJwtProvider(), authenticationManager(), memberRepository))
                .addFilterBefore(filter, LogoutFilter.class)
                .addFilter(corsFilter);

        http
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
//                .antMatchers("/v1/members/login", "/v1/members/join").not().authenticated()
//                .antMatchers("/v1/members", "/v1/members/password").authenticated()
                .anyRequest().permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .mvcMatchers("/v2/**",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/**",
                        "/swagger-resources/**");
    }
}
