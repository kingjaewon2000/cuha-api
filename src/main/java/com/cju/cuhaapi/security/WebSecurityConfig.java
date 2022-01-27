package com.cju.cuhaapi.security;

import com.cju.cuhaapi.member.MemberRepository;
import com.cju.cuhaapi.security.jwt.JwtAuthenticationFilter;
import com.cju.cuhaapi.security.jwt.JwtAuthorizationFilter;
import com.cju.cuhaapi.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final MemberRepository memberRepository;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProvider getJwtProvider() {
        return new JwtProvider();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/user/**").access("hasRole('ROLE_USER')")
                .anyRequest().permitAll();

        http
                .addFilter(new JwtAuthenticationFilter(getJwtProvider(), authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(getJwtProvider(), authenticationManager(), memberRepository))
                .addFilter(corsFilter);
    }
}
