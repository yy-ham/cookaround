package com.project.cookaround.domain.member.service;

import com.project.cookaround.domain.member.entity.Member;
import com.project.cookaround.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long join(Member member) {
        System.out.println("<Service>");
        validateDuplicateMemberByLoginId(member.getLoginId()); // 중복 아이디 검증
        validateDuplicateMemberByEmail(member); // 중복 이메일 검증
        memberRepository.save(member);
        return member.getId();
    }

    public boolean validateDuplicateMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }

    public void validateDuplicateMemberByEmail(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> member = Optional.ofNullable(memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException(loginId)));

        return User.builder()
                .username(member.get().getLoginId())
                .password(member.get().getPassword())
                .build();
    }

}