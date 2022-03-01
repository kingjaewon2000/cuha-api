package com.cju.cuhaapi.config;

import com.cju.cuhaapi.member.Department;
import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.member.MemberDto.*;
import com.cju.cuhaapi.member.Password;
import com.cju.cuhaapi.member.Profile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Configuration
public class BeanConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(JoinRequest.class, Member.class).addMappings(mapping -> {
            mapping.using((Converter<String, Department>) context -> Department.valueOf(context.getSource()))
                    .map(JoinRequest::getDepartment, Member::setDepartment);
            mapping.using((Converter<String, Password>) context -> new Password(context.getSource()))
                    .map(JoinRequest::getPassword, Member::setPassword);
        });

        modelMapper.typeMap(UpdateInfoRequest.class, Member.class).addMappings(mapping -> {
            mapping.using((Converter<String, Department>) context -> Department.valueOf(context.getSource()))
                    .map(UpdateInfoRequest::getDepartment, Member::setDepartment);
        });

        modelMapper.typeMap(UpdatePasswordRequest.class, Member.class).addMappings(mapping -> {
            mapping.using((Converter<String, Password>) context -> new Password(context.getSource()))
                    .map(UpdatePasswordRequest::getPassword, Member::setPassword);
        });

        modelMapper.typeMap(Member.class, InfoResponse.class).addMappings(mapping -> {
            mapping.using((Converter<Profile, String>) context -> context.getSource().getNewFilename())
                    .map(Member::getProfile, InfoResponse::setProfileFilename);
        });

        return modelMapper;
    }
}
