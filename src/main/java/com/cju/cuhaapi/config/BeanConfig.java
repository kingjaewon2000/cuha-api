package com.cju.cuhaapi.config;

import com.cju.cuhaapi.member.Department;
import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.member.MemberDto.JoinRequest;
import com.cju.cuhaapi.member.Password;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
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

        modelMapper.typeMap(JoinRequest.class, Member.class).addMappings(mapper -> {
            mapper.using((Converter<String, Department>) context -> Department.valueOf(context.getSource()))
                    .map(JoinRequest::getDepartment, Member::setDepartment);
            mapper.using((Converter<String, Password>) context -> new Password(context.getSource()))
                    .map(JoinRequest::getPassword, Member::setPassword);
        });

        return modelMapper;
    }
}
