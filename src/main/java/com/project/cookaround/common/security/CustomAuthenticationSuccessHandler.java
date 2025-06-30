package com.project.cookaround.common.security;

import com.project.cookaround.domain.member.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;

    public CustomAuthenticationSuccessHandler(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LocalDateTime lastLoginAt = LocalDateTime.now();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Long id = userDetails.getId();
        String loginId = userDetails.getUsername();
        String savedId = request.getParameter("save-id");

        if ("on".equals(savedId)) {
            Cookie cookie = new Cookie("savedId", loginId);
            cookie.setMaxAge(60 * 60 * 24 * 7); // 7일
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("savedId", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        memberService.updateLastLoginAt(id, lastLoginAt);

        response.sendRedirect("/");
    }

}
