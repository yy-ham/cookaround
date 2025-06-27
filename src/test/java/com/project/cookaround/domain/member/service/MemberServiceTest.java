package com.project.cookaround.domain.member.service;

import com.project.cookaround.domain.member.entity.Member;
import com.project.cookaround.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        // given
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test");
        member.setEmail("test@test.com");

        // when
        Long savaId = memberService.join(member);

        // then
        Optional<Member> saveMember = memberRepository.findOne(savaId);
        assertThat(saveMember).isPresent();
        assertThat(member).isEqualTo(saveMember.get());
    }

    @Test
    public void 아이디_중복_회원() {
        // given
        Member member1 = new Member();
        member1.setLoginId("test");
        member1.setPassword("test1");
        member1.setEmail("test1@test.com");

        Member member2 = new Member();
        member2.setLoginId("test");
        member2.setPassword("test2");
        member2.setEmail("test2@test.com");

        // when
        memberService.join(member1);

        // then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }

    @Test
    public void 이메일_중복_회원() {
        // given
        Member member1 = new Member();
        member1.setLoginId("test1");
        member1.setPassword("test1");
        member1.setEmail("test@test.com");

        Member member2 = new Member();
        member2.setLoginId("test2");
        member2.setPassword("test2");
        member2.setEmail("test@test.com");

        // when
        memberService.join(member1);

        // then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
    }

}