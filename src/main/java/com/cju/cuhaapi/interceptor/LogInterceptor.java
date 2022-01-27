package com.cju.cuhaapi.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        request.setAttribute("uuid", uuid);
        String requestURL = request.getRequestURL().toString();
        String method = request.getMethod();

        log.info("[{}][{}][{}]: {}", uuid, requestURL, method);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uuid = request.getAttribute("uuid").toString();
        String requestURL = request.getRequestURL().toString();
        String method = request.getMethod();

        log.info("[{}][{}][{}]: {}", uuid, requestURL, method);
    }
}
