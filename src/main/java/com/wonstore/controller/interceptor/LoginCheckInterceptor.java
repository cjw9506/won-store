package com.wonstore.controller.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonstore.dto.apiDto.login.LoginResponse;
import com.wonstore.entity.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");
            //로그인으로 redirect
            //response.sendRedirect("/login?redirectURL=" + requestURI);
            LoginResponse loginResponse = new LoginResponse(false, "인증 실패");
            String responseJson = new ObjectMapper().writeValueAsString(loginResponse);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(responseJson);
            return false;
        }
        log.info("인증 성공");
        return true;
    }
}
