package com.cju.cuhaapi.config;

import com.cju.cuhaapi.member.Department;
import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.member.MemberDto.InfoResponse;
import com.cju.cuhaapi.member.MemberDto.JoinRequest;
import com.cju.cuhaapi.member.MemberDto.UpdateInfoRequest;
import com.cju.cuhaapi.member.MemberDto.UpdatePasswordRequest;
import com.cju.cuhaapi.member.Password;
import com.cju.cuhaapi.member.Profile;
import com.cju.cuhaapi.post.Post;
import com.cju.cuhaapi.post.PostDto.GetResponse;
import com.cju.cuhaapi.common.TimeEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

        // Member Domain
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
            mapping.using((Converter<Department, String>) context -> context.getSource().getDescription())
                    .map(Member::getDepartment, InfoResponse::setDepartment);
            mapping.using((Converter<Profile, String>) context -> context.getSource().getNewFilename())
                    .map(Member::getProfile, InfoResponse::setProfileFilename);
        });

        // Post Domain
        modelMapper.typeMap(Post.class, GetResponse.class).addMappings(mapping -> {
            mapping.using((Converter<Member, String>) context -> context.getSource().getUsername())
                    .map(Post::getMember, GetResponse::setUsername);
            mapping.using((Converter<TimeEntity, String>) context -> context.getSource().getCreatedAt().toString())
                    .map(Post::getTimeEntity, GetResponse::setCreatedAt);
        });

        return modelMapper;
    }
}
