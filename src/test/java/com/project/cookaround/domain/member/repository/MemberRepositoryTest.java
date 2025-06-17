package com.project.cookaround.domain.member.repository;

import com.project.cookaround.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void save() {
        // given
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test");
        member.setEmail("test@test.com");

        // when
        memberRepository.save(member);

        // then
        Optional<Member> saveMember = memberRepository.findOne(member.getId());
        assertThat(saveMember).isPresent();
        assertThat(member).isEqualTo(saveMember.get());
    }

    @Test
    public void findByLoginId() {
        // given
        Member member1 = new Member();
        member1.setLoginId("test1");
        member1.setPassword("test1");
        member1.setEmail("test1@test.com");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setLoginId("test2");
        member2.setPassword("test2");
        member2.setEmail("test2@test.com");
        memberRepository.save(member2);

        // when
        Optional<Member> findMember = memberRepository.findByLoginId("test2");

        // then
        assertThat(findMember).isPresent();
        assertThat(member2).isEqualTo(findMember.get());
    }

    @Test
    public void findByLoginId_null() {
        // given
        Member member1 = new Member();
        member1.setLoginId("test1");
        member1.setPassword("test1");
        member1.setEmail("test1@test.com");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setLoginId("test2");
        member2.setPassword("test2");
        member2.setEmail("test2@test.com");
        memberRepository.save(member2);

        // when
        Optional<Member> findMember = memberRepository.findByLoginId("test3");

        // then
        assertThat(findMember).isEmpty();
    }

    @Test
    public void findByEmail() {
        // given
        Member member1 = new Member();
        member1.setLoginId("test1");
        member1.setPassword("test1");
        member1.setEmail("test1@test.com");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setLoginId("test2");
        member2.setPassword("test2");
        member2.setEmail("test2@test.com");
        memberRepository.save(member2);

        // when
        Optional<Member> findMember = memberRepository.findByEmail("test1@test.com");

        // then
        assertThat(findMember).isPresent();
        assertThat(member1).isEqualTo(findMember.get());
    }

    @Test
    public void findByEmail_null() {
        // given
        Member member1 = new Member();
        member1.setLoginId("test1");
        member1.setPassword("test1");
        member1.setEmail("test1@test.com");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setLoginId("test2");
        member2.setPassword("test2");
        member2.setEmail("test2@test.com");
        memberRepository.save(member2);

        // when
        Optional<Member> findMember = memberRepository.findByEmail("test3@test.com");

        // then
        assertThat(findMember).isEmpty();
    }

}