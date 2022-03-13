//package com.cju.cuhaapi.mapper;
//
//import com.cju.cuhaapi.domain.member.entity.Member;
//import com.cju.cuhaapi.domain.member.dto.MemberDto.JoinRequest;
//import com.cju.cuhaapi.domain.member.dto.MemberDto.LoginRequest;
//import com.cju.cuhaapi.domain.member.dto.MemberDto.UpdateInfoRequest;
//import com.cju.cuhaapi.domain.member.dto.MemberDto.UpdatePasswordRequest;
//import org.junit.jupiter.api.Test;
//
//class MemberMapperTest {
//
//    @Test
//    void joinRequestToEntity() {
//        JoinRequest joinRequest = JoinRequest.builder()
//                .username("test")
//                .password("test")
//                .name("김태형")
//                .isMale(true)
//                .email("kkk@naver.com")
//                .phoneNumber("010-0000-0000")
//                .studentId("2019010109")
//                .department("DIGITAL_SECURITY")
//                .build();
//
//        Member member = MemberMapper.INSTANCE.joinRequestToEntity(joinRequest);
//        System.out.println(member);
//    }
//
//    @Test
//    void loginRequestToEntity() {
//        LoginRequest loginRequest = LoginRequest.builder()
//                .username("test")
//                .password("test")
//                .build();
//
//        Member member = MemberMapper.INSTANCE.loginRequestToEntity(loginRequest);
//    }
//
//    @Test
//    void toJoinRequest() {
//        JoinRequest joinRequest = JoinRequest.builder()
//                .username("test")
//                .password("test")
//                .name("김태형")
//                .isMale(true)
//                .email("kkk@naver.com")
//                .phoneNumber("010-0000-0000")
//                .studentId("2019010109")
//                .department("DIGITAL_SECURITY")
//                .build();
//
//        Member member = MemberMapper.INSTANCE.joinRequestToEntity(joinRequest);
//    }
//
//    @Test
//    void toLoginRequest() {
//        LoginRequest loginRequest = LoginRequest.builder()
//                .username("test")
//                .password("test")
//                .build();
//
//        Member member = MemberMapper.INSTANCE.loginRequestToEntity(loginRequest);
//    }
//
//    @Test
//    void updateInfoRequestToEntity() {
//        JoinRequest joinRequest = JoinRequest.builder()
//                .username("test")
//                .password("test")
//                .name("김태형")
//                .isMale(true)
//                .email("kkk@naver.com")
//                .phoneNumber("010-0000-0000")
//                .studentId("2019010109")
//                .department("DIGITAL_SECURITY")
//                .build();
//
//        Member member = MemberMapper.INSTANCE.joinRequestToEntity(joinRequest);
//        System.out.println(member);
//
//        UpdateInfoRequest updateInfoRequest = UpdateInfoRequest.builder()
//                .name("김태형")
//                .isMale(true)
//                .email("kkk@naver.com")
//                .phoneNumber("010-1234-5678")
//                .studentId("2000010101")
//                .department("DIGITAL_SECURITY")
//                .build();
//
//        Member updatedMember = MemberMapper.INSTANCE.updateInfoRequestToEntity(updateInfoRequest, member);
//        System.out.println(updatedMember);
//    }
//
//    @Test
//    void updatePasswordRequestToEntity() {
//        JoinRequest joinRequest = JoinRequest.builder()
//                .username("test")
//                .password("test")
//                .name("김태형")
//                .isMale(true)
//                .email("kkk@naver.com")
//                .phoneNumber("010-0000-0000")
//                .studentId("2019010109")
//                .department("DIGITAL_SECURITY")
//                .build();
//
//        Member member = MemberMapper.INSTANCE.joinRequestToEntity(joinRequest);
//        System.out.println(member);
//
//        UpdatePasswordRequest updatePasswordRequest = UpdatePasswordRequest.builder()
//                .passwordBefore("test")
//                .passwordAfter("tttt")
//                .build();
//
//        Member updatedMember = MemberMapper.INSTANCE.updatePasswordRequestToEntity(updatePasswordRequest, member);
//        System.out.println(updatedMember);
//    }
//}