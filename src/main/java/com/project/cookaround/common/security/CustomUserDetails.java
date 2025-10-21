package com.project.cookaround.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Long id; // 회원 번호
    private final String username; // 로그인 아이디
    private final String password; // 비밀번호
    private final String profile; //프로필 이미지

    public CustomUserDetails(Long id, String username, String password, String profile) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Long getId() {
        return this.id;
    }

    public String getProfile() {
        return this.profile;
    }

}
