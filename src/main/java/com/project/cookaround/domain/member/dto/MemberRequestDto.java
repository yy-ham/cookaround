package com.project.cookaround.domain.member.dto;

import com.project.cookaround.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberRequestDto {

    private String loginId;
    private String password;
    private String email;
    private String profile;


    // Dto -> Entity 변환
    public Member toEntity() {
        Member member = new Member();
        member.setLoginId(this.loginId);
        member.setPassword(this.password);
        member.setEmail(this.email);
        member.setProfile(this.profile);
        return member;
    }

}
