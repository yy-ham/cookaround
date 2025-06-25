package com.project.cookaround.domain.member.service;

import com.project.cookaround.domain.member.entity.Member;
import com.project.cookaround.domain.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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
