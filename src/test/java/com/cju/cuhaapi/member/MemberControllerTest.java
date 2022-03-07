//package com.cju.cuhaapi.member;
//
//import com.cju.cuhaapi.member.MemberDto.*;
//import com.cju.cuhaapi.security.jwt.JwtResponseDto;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DisplayName("MemberController 테스트")
//class MemberControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    private String accessToken;
//
//    @BeforeEach
//    void beforeEach() throws Exception {
//        Password password = new Password("cju");
//
//        Member member = Member.builder()
//                .username("cuha")
//                .password(password)
//                .name("김태형")
//                .email("example@cju.ac.kr")
//                .isMale(true)
//                .phoneNumber("010-0000-0000")
//                .studentNumber("2019010109")
//                .department(Department.DIGITAL_SECURITY)
//                .build();
//
//        memberRepository.save(member);
//
//        엑세스_토큰_발급();
//    }
//
//    @AfterEach
//    void afterEach() {
//        memberRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("멤버 조회 테스트")
//    void 멤버_조회() throws Exception {
//        String content = mvc.perform(get("/v1/members")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        InfoResponse infoResponse = objectMapper.readValue(content, InfoResponse.class);
//        assertEquals(infoResponse.getUsername(), "cuha");
////        assertEquals(infoResponse.getName(), "김태형");
//        assertEquals(infoResponse.getEmail(), "example@cju.ac.kr");
//        assertEquals(infoResponse.getPhoneNumber(), "010-0000-0000");
//        assertEquals(infoResponse.getStudentNumber(), "2019010109");
//        assertEquals(infoResponse.getDepartment(), Department.DIGITAL_SECURITY);
//    }
//
//    @Test
//    @DisplayName("회원가입 테스트")
//    void 회원가입() throws Exception {
//        JoinRequest joinRequest = JoinRequest.builder()
//                .username("apple")
//                .password("apple")
//                .name("김태형")
//                .email("example@cju.ac.kr")
//                .isMale(true)
//                .phoneNumber("010-1234-5678")
//                .studentNumber("00000000000")
//                .department("DIGITAL_SECURITY")
//                .build();
//
//        String json = objectMapper.writeValueAsString(joinRequest);
//
//        String content = mvc.perform(post("/v1/members/join")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//
//        InfoResponse infoResponse = objectMapper.readValue(content, InfoResponse.class);
//        assertEquals(infoResponse.getUsername(), "apple");
////        assertEquals(infoResponse.getName(), "김태형");
//        assertEquals(infoResponse.getEmail(), "example@cju.ac.kr");
//        assertEquals(infoResponse.getPhoneNumber(), "010-1234-5678");
//        assertEquals(infoResponse.getStudentNumber(), "00000000000");
//        assertEquals(infoResponse.getDepartment(), Department.DIGITAL_SECURITY);
//    }
//
//    @Test
//    @DisplayName("로그인 테스트")
//    void 로그인() throws Exception {
//        LoginRequest loginRequest = LoginRequest.builder()
//                .username("cuha")
//                .password("cju")
//                .build();
//
//        String json = objectMapper.writeValueAsString(loginRequest);
//
//        mvc.perform(post("/v1/members/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("멤버 정보 변경 테스트")
//    void 멤버_정보_변경() throws Exception {
//        UpdateInfoRequest updateInfoRequest = UpdateInfoRequest.builder()
//                .name("테스트")
//                .email("test@cju.ac.kr")
//                .isMale(false)
//                .phoneNumber("010-9876-5432")
//                .studentId("1111111111")
//                .department("DIGITAL_SECURITY")
//                .build();
//
//        String json = objectMapper.writeValueAsString(updateInfoRequest);
//
//        mvc.perform(patch("/v1/members")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("비밀번호 변경 테스트")
//    void 비밀번호_변경() throws Exception {
//        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
//        updatePasswordRequest.setPassword("qwer");
//
//        String json = objectMapper.writeValueAsString(updatePasswordRequest);
//
//
//        mvc.perform(patch("/v1/members/password")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("회원탈퇴 테스트")
//    void 회원탈퇴() throws Exception {
//        mvc.perform(delete("/v1/members")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(""))
//                .andExpect(status().isOk());
//    }
//
//    private void 엑세스_토큰_발급() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("cuha");
//        loginRequest.setPassword("cju");
//
//        String json = objectMapper.writeValueAsString(loginRequest);
//
//        ResultActions perform = mvc.perform(post("/v1/members/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json));
//
//        String content = perform.andReturn().getResponse().getContentAsString();
//        JwtResponseDto responseDto = objectMapper.readValue(content, JwtResponseDto.class);
//
//        this.accessToken = responseDto.getToken().getAccessToken();
//    }
//}