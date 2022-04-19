package com.cju.cuhaapi.member.dto;

import com.cju.cuhaapi.member.domain.entity.Department;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.member.domain.entity.Role;
import lombok.*;

import javax.validation.constraints.NotNull;

public class MemberDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class LoginRequest {
        @NotNull
        private String username;

        @NotNull
        private String password;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class JoinRequest {
        @NotNull
        private String username;

        @NotNull
        private String password;

        @NotNull
        private String name;
        private Boolean isMale;
        private String email;
        private String phoneNumber;
        private String studentId;
        private Department department;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdateMemberRequest {
        private String name;
        private Boolean isMale;
        private String email;
        private String phoneNumber;
        private String studentId;
        private Department department;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdatePasswordRequest {
        private String passwordBefore;
        private String passwordAfter;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class MemberResponse {
        private String profileImage;
        private String username;
        private String name;
        private Boolean isMale;
        private String email;
        private String phoneNumber;
        private String studentId;
        private Department department;
        private int score;
        private Role role;
        private String createdAt;
        private String updatedAt;
        private String lastModifiedDate;

        public static MemberResponse of(Member member) {
            return MemberResponse.builder()
                    .profileImage(member.getProfile().getFilename())
                    .username(member.getUsername())
                    .name(member.getName())
                    .isMale(member.getIsMale())
                    .email(member.getEmail())
                    .phoneNumber(member.getPhoneNumber())
                    .studentId(member.getStudentId())
                    .department(member.getDepartment())
                    .score(member.getScore())
                    .role(Role.builder()
                            .id(member.getRole().getId())
                            .role(member.getRole().getRole())
                            .description(member.getRole().getDescription())
                            .build())
                    .createdAt(member.getCreatedAt().toString())
                    .updatedAt(member.getUpdatedAt().toString())
                    .lastModifiedDate(member.getPassword().getLastModifiedDate().toString())
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class RankingResponse {
        private Long ranking;
        private String username;

        public static RankingResponse of(Long ranking, String username) {
            return RankingResponse.builder()
                    .ranking(ranking)
                    .username(username)
                    .build();
        }
    }

}
