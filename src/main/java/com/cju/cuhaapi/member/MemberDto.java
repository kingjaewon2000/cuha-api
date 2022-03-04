package com.cju.cuhaapi.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class MemberDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginRequest {
        @ApiModelProperty(value = "아이디", required = true, example = "cuha")
        @NotNull
        private String username;

        @ApiModelProperty(value = "비밀번호", required = true, example = "cju")
        @NotNull
        private String password;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class JoinRequest {
        @ApiModelProperty(value = "아이디", required = true, example = "cuha")
        @NotNull
        private String username;

        @ApiModelProperty(value = "비밀번호", required = true, example = "cju")
        @NotNull
        private String password;

        @ApiModelProperty(value = "이전 비밀번호", required = true, example = "cju")
        @NotNull
        private String repeatPassword;

        @ApiModelProperty(value = "이름", required = true, example = "김태형")
        @NotNull
        private String name;

        @ApiModelProperty(value = "성별", required = true, notes = "필수값", example = "true")
        private Boolean isMale;

        @ApiModelProperty(value = "이메일", example = "example@cju.ac.kr")
        private String email;

        @ApiModelProperty(value = "전화번호", example = "010-0000-0000")
        private String phoneNumber;

        @ApiModelProperty(value = "학번", required = true, example = "2019010109")
        private String studentNumber;

        @ApiModelProperty(value = "학과", required = true, example = "DIGITAL_SECURITY")
        private String department;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateInfoRequest {
        @ApiModelProperty(value = "이름", required = true, example = "김태형")
        private String name;

        @ApiModelProperty(value = "성별", required = true, notes = "필수값", example = "true")
        private Boolean isMale;

        @ApiModelProperty(value = "이메일", example = "example@cju.ac.kr")
        private String email;

        @ApiModelProperty(value = "전화번호", example = "010-0000-0000")
        private String phoneNumber;

        @ApiModelProperty(value = "학번", required = true, example = "2019010109")
        private String studentNumber;

        @ApiModelProperty(value = "학과", required = true, example = "DIGITAL_SECURITY")
        private String department;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class UpdatePasswordRequest {
        @ApiModelProperty(value = "이전 비밀번호", required = true, example = "cju")
        private String oldPassword;

        @ApiModelProperty(value = "비밀번호", required = true, example = "cju2022")
        private String password;

        @ApiModelProperty(value = "이전 비밀번호", required = true, example = "cju2022")
        private String repeatPassword;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InfoResponse {
        @ApiModelProperty(value = "프로필")
        private String profileFilename;

        @ApiModelProperty(value = "아이디", example = "cuha")
        private String username;

        @ApiModelProperty(value = "이름", example = "김태형")
        private String name;

        @ApiModelProperty(value = "성별", example = "true")
        private Boolean isMale;

        @ApiModelProperty(value = "이메일", example = "example@cju.ac.kr")
        private String email;

        @ApiModelProperty(value = "전화번호", example = "010-0000-0000")
        private String phoneNumber;

        @ApiModelProperty(value = "학번", example = "2019010109")
        private String studentNumber;

        @ApiModelProperty(value = "학과", example = "DIGITAL_SECURITY")
        private String department;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class IdResponse {
        @ApiModelProperty(value = "식별자", example = "1")
        private Long id;
    }
}
