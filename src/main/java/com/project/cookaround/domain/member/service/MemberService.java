package com.project.cookaround.domain.member.service;

import com.project.cookaround.domain.member.entity.Member;
import com.project.cookaround.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long join(Member member) {
        System.out.println("<Service>");
        validateDuplicateMemberByLoginId(member.getLoginId()); // 중복 아이디 검증
        validateDuplicateMemberByEmail(member.getEmail()); // 중복 이메일 검증
        memberRepository.save(member);
        return member.getId();
    }

    public boolean validateDuplicateMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }

    public boolean validateDuplicateMemberByEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

}