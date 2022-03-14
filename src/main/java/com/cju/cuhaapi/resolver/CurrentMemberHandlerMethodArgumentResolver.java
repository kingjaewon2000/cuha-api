package com.cju.cuhaapi.resolver;

import com.cju.cuhaapi.annotation.CurrentMember;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.security.auth.PrincipalDetails;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import springfox.documentation.spi.service.contexts.SecurityContext;

public class CurrentMemberHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = ((PrincipalDetails)authentication.getPrincipal());
        if (principalDetails == null) {
            throw new IllegalArgumentException("멤버를 찾지 못했습니다.");
        }

        Member authMember = principalDetails.getMember();
        if (authMember == null) {
            throw new IllegalArgumentException("멤버를 찾지 못했습니다.");
        }


        return authMember;
    }
}
