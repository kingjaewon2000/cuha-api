package com.cju.cuhaapi.mapper;

import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.member.MemberDto.*;
import com.cju.cuhaapi.member.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "profile", ignore = true),
            @Mapping(target = "refreshToken", ignore = true),
            @Mapping(source = "password", target = "password.value"),
            @Mapping(target = "baseTime.createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "baseTime.updatedAt", expression = "java(java.time.LocalDateTime.now())")
    })
    Member joinRequestToEntity(JoinRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "name", ignore = true),
            @Mapping(target = "isMale", ignore = true),
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "phoneNumber", ignore = true),
            @Mapping(target = "studentId", ignore = true),
            @Mapping(target = "department", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "profile", ignore = true),
            @Mapping(target = "refreshToken", ignore = true),
            @Mapping(target = "baseTime", ignore = true),
            @Mapping(source = "password", target = "password.value")
    })
    Member loginRequestToEntity(LoginRequest request);

    @Mappings({
            @Mapping(source = "member.id", target = "id"),
            @Mapping(source = "member.username", target = "username"),
            @Mapping(source = "member.password", target = "password"),
            @Mapping(source = "member.name", target = "name"),
            @Mapping(source = "member.isMale", target = "isMale"),
            @Mapping(source = "member.email", target = "email"),
            @Mapping(source = "member.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "member.studentId", target = "studentId"),
            @Mapping(source = "member.department", target = "department"),
            @Mapping(source = "member.role", target = "role"),
            @Mapping(source = "request", target = "profile"),
            @Mapping(source = "member.refreshToken", target = "refreshToken"),
            @Mapping(source = "member.baseTime.createdAt", target = "baseTime.createdAt"),
            @Mapping(target = "baseTime.updatedAt", expression = "java(java.time.LocalDateTime.now())")
    })
    Member updateProfileToEntity(Profile request, Member member);

    @Mappings({
            @Mapping(source = "member.id", target = "id"),
            @Mapping(source = "member.username", target = "username"),
            @Mapping(source = "member.password", target = "password"),
            @Mapping(source = "request.name", target = "name"),
            @Mapping(source = "request.isMale", target = "isMale"),
            @Mapping(source = "request.email", target = "email"),
            @Mapping(source = "request.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "request.studentId", target = "studentId"),
            @Mapping(source = "request.department", target = "department"),
            @Mapping(source = "member.role", target = "role"),
            @Mapping(source = "member.profile", target = "profile"),
            @Mapping(source = "member.refreshToken", target = "refreshToken"),
            @Mapping(source = "member.baseTime.createdAt", target = "baseTime.createdAt"),
            @Mapping(target = "baseTime.updatedAt", expression = "java(java.time.LocalDateTime.now())")
    })
    Member updateInfoRequestToEntity(UpdateInfoRequest request, Member member);

    @Mappings({
            @Mapping(source = "member.id", target = "id"),
            @Mapping(source = "member.username", target = "username"),
            @Mapping(source = "request.passwordAfter", target = "password.value"),
            @Mapping(source = "member.name", target = "name"),
            @Mapping(source = "member.isMale", target = "isMale"),
            @Mapping(source = "member.email", target = "email"),
            @Mapping(source = "member.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "member.studentId", target = "studentId"),
            @Mapping(source = "member.department", target = "department"),
            @Mapping(source = "member.role", target = "role"),
            @Mapping(source = "member.profile", target = "profile"),
            @Mapping(source = "member.refreshToken", target = "refreshToken"),
            @Mapping(source = "member.baseTime", target = "baseTime")
    })
    Member updatePasswordRequestToEntity(UpdatePasswordRequest request, Member member);

    @Mappings({
            @Mapping(source = "member.profile.filename", target = "profileImage"),
            @Mapping(source = "member.baseTime.createdAt", target = "createdAt"),
            @Mapping(source = "member.baseTime.updatedAt", target = "updatedAt"),
            @Mapping(source = "member.password.lastModifiedDate", target = "lastModifiedDate")
    })
    InfoResponse toInfoResponse(Member member);
}
