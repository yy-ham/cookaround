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
        validateDuplicateMemberByLoginId(member); // 중복 아이디 검증
        validateDuplicateMemberByEmail(member); // 중복 이메일 검증
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMemberByLoginId(Member member) {
        if (memberRepository.findByLoginId(member.getLoginId()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    public void validateDuplicateMemberByEmail(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

}