package com.cju.cuhaapi.mapper;

import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.member.MemberDto;
import com.cju.cuhaapi.member.MemberDto.JoinRequest;
import com.cju.cuhaapi.member.MemberDto.LoginRequest;
import com.cju.cuhaapi.member.MemberDto.UpdateInfoRequest;
import com.cju.cuhaapi.member.MemberDto.UpdatePasswordRequest;
import com.cju.cuhaapi.member.Password;
import com.cju.cuhaapi.utils.PasswordEncoderUtils;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import static org.junit.jupiter.api.Assertions.*;

class MemberMapperTest {

    @Test
    void joinRequestToEntity() {
        JoinRequest joinRequest = JoinRequest.builder()
                .username("test")
                .password("test")
                .name("김태형")
                .isMale(true)
                .email("kkk@naver.com")
                .phoneNumber("010-0000-0000")
                .studentId("2019010109")
                .department("DIGITAL_SECURITY")
                .build();

        Member member = MemberMapper.INSTANCE.joinRequestToEntity(joinRequest);
        System.out.println(member);
    }

    @Test
    void loginRequestToEntity() {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("test")
                .password("test")
                .build();

        Member member = MemberMapper.INSTANCE.loginRequestToEntity(loginRequest);
    }

    @Test
    void toJoinRequest() {
        JoinRequest joinRequest = JoinRequest.builder()
                .username("test")
                .password("test")
                .name("김태형")
                .isMale(true)
                .email("kkk@naver.com")
                .phoneNumber("010-0000-0000")
                .studentId("2019010109")
                .department("DIGITAL_SECURITY")
                .build();

        Member member = MemberMapper.INSTANCE.joinRequestToEntity(joinRequest);
    }

    @Test
    void toLoginRequest() {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("test")
                .password("test")
                .build();

        Member member = MemberMapper.INSTANCE.loginRequestToEntity(loginRequest);
    }

    @Test
    void updateInfoRequestToEntity() {
        JoinRequest joinRequest = JoinRequest.builder()
                .username("test")
                .password("test")
                .name("김태형")
                .isMale(true)
                .email("kkk@naver.com")
                .phoneNumber("010-0000-0000")
                .studentId("2019010109")
                .department("DIGITAL_SECURITY")
                .build();

        Member member = MemberMapper.INSTANCE.joinRequestToEntity(joinRequest);
        System.out.println(member);

        UpdateInfoRequest updateInfoRequest = UpdateInfoRequest.builder()
                .name("김태형")
                .isMale(true)
                .email("kkk@naver.com")
                .phoneNumber("010-1234-5678")
                .studentId("2000010101")
                .department("DIGITAL_SECURITY")
                .build();

        Member updatedMember = MemberMapper.INSTANCE.updateInfoRequestToEntity(updateInfoRequest, member);
        System.out.println(updatedMember);
    }

    @Test
    void updatePasswordRequestToEntity() {
        JoinRequest joinRequest = JoinRequest.builder()
                .username("test")
                .password("test")
                .name("김태형")
                .isMale(true)
                .email("kkk@naver.com")
                .phoneNumber("010-0000-0000")
                .studentId("2019010109")
                .department("DIGITAL_SECURITY")
                .build();

        Member member = MemberMapper.INSTANCE.joinRequestToEntity(joinRequest);
        System.out.println(member);

        UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
                .passwordBefore("test")
                .passwordAfter("tttt")
                .build();

        Member updatedMember = MemberMapper.INSTANCE.updatePasswordRequestToEntity(updatePasswordRequest, member);
        System.out.println(updatedMember);
    }
}