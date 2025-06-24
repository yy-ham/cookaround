package com.project.cookaround.domain.member.entity;

public enum EmailVerificationResult {
    
    SUCCESS, // 인증 성공
    EXPIRED, // 인증 시간 초과
    INVALID // 이메일 또는 인증코드 불일치

}
