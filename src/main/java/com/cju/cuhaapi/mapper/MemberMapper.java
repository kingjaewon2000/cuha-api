package com.cju.cuhaapi.mapper;

import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.member.dto.MemberDto.*;
import com.cju.cuhaapi.domain.member.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mappings({
            @Mapping(source = "password", target = "password.value"),
    })
    Member joinRequestToEntity(JoinRequest request);

    @Mappings({
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
    InfoResponse entityToInfoResponse(Member member);
}
