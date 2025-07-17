package com.project.cookaround.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class ResetPasswordInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 아이디 찾기 -> 비밀번호 재설정 흐름 벗어나는 경우, 세션에서 인증 정보 삭제
        HttpSession session = request.getSession(false);
        String requestUri = request.getRequestURI();

        if (session != null) {
            if (!(requestUri.startsWith("/members/reset-password") ||
                    requestUri.startsWith("/members/find-password") ||
                    requestUri.startsWith("/members/find-id"))) {

                session.removeAttribute("verifiedLoginId");
                session.removeAttribute("verifiedEmail");

            }
        }

        return true; // 컨트롤러 호출
    }

}
