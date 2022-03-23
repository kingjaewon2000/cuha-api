package com.cju.cuhaapi.domain.member.dto;

import com.cju.cuhaapi.domain.member.entity.Department;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.member.entity.Password;
import com.cju.cuhaapi.domain.member.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class MemberDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class LoginRequest {
        @ApiModelProperty(value = "아이디", required = true, example = "cuha")
        @NotNull
        private String username;

        @ApiModelProperty(value = "비밀번호", required = true, example = "cju")
        @NotNull
        private String password;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class JoinRequest {
        @ApiModelProperty(value = "아이디", required = true, example = "cuha")
        @NotNull
        private String username;

        @ApiModelProperty(value = "비밀번호", required = true, example = "cju")
        @NotNull
        private String password;

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
        private String studentId;

        @ApiModelProperty(value = "학과", required = true, example = "DIGITAL_SECURITY")
        private String department;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
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
        private String studentId;

        @ApiModelProperty(value = "학과", required = true, example = "DIGITAL_SECURITY")
        private String department;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdatePasswordRequest {
        @ApiModelProperty(value = "이전 비밀번호", required = true, example = "cju")
        private String passwordBefore;

        @ApiModelProperty(value = "비밀번호", required = true, example = "cju2022")
        private String passwordAfter;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class InfoResponse {
        @ApiModelProperty(value = "프로필")
        private String profileImage;

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
        private String studentId;

        @ApiModelProperty(value = "학과", example = "DIGITAL_SECURITY")
        private String department;

        @ApiModelProperty(value = "권한", example = "동아리원")
        private Role role;

        @ApiModelProperty(value = "계정 생성 일")
        private String createdAt;

        @ApiModelProperty(value = "계정 수정 일")
        private String updatedAt;

        @ApiModelProperty(value = "패스워드 마지막 수정 일")
        private String lastModifiedDate;

        public static InfoResponse of(Member member) {
            return InfoResponse.builder()
                    .profileImage(member.getProfile().getFilename())
                    .username(member.getUsername())
                    .name(member.getName())
                    .isMale(member.getIsMale())
                    .email(member.getEmail())
                    .phoneNumber(member.getPhoneNumber())
                    .studentId(member.getStudentId())
                    .department(member.getDepartment().name())
                    .role(Role.builder()
                            .id(member.getRole().getId())
                            .role(member.getRole().getRole())
                            .description(member.getRole().getDescription())
                            .build())
                    .createdAt(member.getBaseTime().getCreatedAt().toString())
                    .updatedAt(member.getBaseTime().getUpdatedAt().toString())
                    .lastModifiedDate(member.getPassword().getLastModifiedDate().toString())
                    .build();
        }
    }

}
