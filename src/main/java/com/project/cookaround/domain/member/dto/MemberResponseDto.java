package com.project.cookaround.domain.member.dto;

import com.project.cookaround.domain.member.entity.Member;
import com.project.cookaround.domain.member.entity.MemberStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MemberResponseDto {

    private Long id;
    private String loginId;
    private String email;
    private String profile;
    private MemberStatus status;
    private LocalDateTime joinAt;
    private LocalDateTime lastLoginAt;


    // Entity -> Dto 변환
    public static MemberResponseDto fromEntity(Member member) {
        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setId(member.getId());
        memberResponseDto.setLoginId(member.getLoginId());
        memberResponseDto.setEmail(member.getEmail());
        memberResponseDto.setProfile(member.getProfile());
        memberResponseDto.setStatus(member.getStatus());
        memberResponseDto.setJoinAt(member.getJoinAt());
        memberResponseDto.setLastLoginAt(member.getLastLoginAt());
        return memberResponseDto;
    }
}
