package com.project.cookaround.domain.cookingtip.repository;

import com.project.cookaround.domain.cookingtip.entity.CookingTip;
import com.project.cookaround.domain.cookingtip.entity.CookingTipCategory;
import com.project.cookaround.domain.member.entity.Member;
import com.project.cookaround.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CookingTipRepositoryTest {

    @Autowired CookingTipRepository cookingTipRepository;
    @Autowired MemberRepository memberRepository;

    private Member member;


    @BeforeEach
    public void createMember() {
        member = new Member();
        member.setLoginId("test2");
        member.setPassword("test2");
        member.setEmail("test2@test.com");

        memberRepository.save(member);
    }


    @Test
    @DisplayName("요리팁 등록")
    public void save() {
        //given
        CookingTip cookingTip = new CookingTip();
        cookingTip.setMember(member);
        cookingTip.setCategory(CookingTipCategory.PREPARATION);
        cookingTip.setTitle("title");
        cookingTip.setContent("content");

        //when
        cookingTipRepository.save(cookingTip);

        //then
        CookingTip savedCookingTip = cookingTipRepository.findById(cookingTip.getId())
                .orElseThrow(() -> new NoSuchElementException("요리팁을 찾을 수 없습니다."));

        assertThat(cookingTip).isEqualTo(savedCookingTip);
    }

    @Test
    @DisplayName("요리팁 전체 조회")
    public void findAll() {
        //given
        CookingTip cookingTip1 = new CookingTip();
        cookingTip1.setMember(member);
        cookingTip1.setCategory(CookingTipCategory.PREPARATION);
        cookingTip1.setTitle("title1");
        cookingTip1.setContent("content1");
        cookingTipRepository.save(cookingTip1);

        CookingTip cookingTip2 = new CookingTip();
        cookingTip2.setMember(member);
        cookingTip2.setCategory(CookingTipCategory.PREPARATION);
        cookingTip2.setTitle("title2");
        cookingTip2.setContent("content2");
        cookingTipRepository.save(cookingTip2);

        //when
        List<CookingTip> cookingTips = cookingTipRepository.findAll();

        //then
        assertThat(cookingTips).hasSize(2);
        assertThat(cookingTips).containsExactlyInAnyOrder(cookingTip1, cookingTip2);
        assertThat(cookingTips).extracting("id").containsExactlyInAnyOrder(cookingTip1.getId(), cookingTip2.getId());
    }

    @Test
    @DisplayName("요리팁 번호로 조회")
    public void findById() {
        //given
        CookingTip cookingTip = new CookingTip();
        cookingTip.setMember(member);
        cookingTip.setCategory(CookingTipCategory.PREPARATION);
        cookingTip.setTitle("title");
        cookingTip.setContent("content");
        cookingTipRepository.save(cookingTip);

        //when
        CookingTip foundCookingTip = cookingTipRepository.findById(cookingTip.getId())
                .orElseThrow(() -> new NoSuchElementException("요리팁을 찾을 수 없습니다."));

        //then
        assertThat(cookingTip).isEqualTo(foundCookingTip);
        assertThat(cookingTip.getId()).isEqualTo(foundCookingTip.getId());
    }

    @Test
    @DisplayName("요리팁 카테고리로 조회")
    public void findByCategory() {
        //given
        CookingTip cookingTip1 = new CookingTip();
        cookingTip1.setMember(member);
        cookingTip1.setCategory(CookingTipCategory.PREPARATION);
        cookingTip1.setTitle("title1");
        cookingTip1.setContent("content1");
        cookingTipRepository.save(cookingTip1);

        CookingTip cookingTip2 = new CookingTip();
        cookingTip2.setMember(member);
        cookingTip2.setCategory(CookingTipCategory.PREPARATION);
        cookingTip2.setTitle("title2");
        cookingTip2.setContent("content2");
        cookingTipRepository.save(cookingTip2);

        //when
        List<CookingTip> cookingTips = cookingTipRepository.findByCategory(CookingTipCategory.PREPARATION);

        //then
        assertThat(cookingTips).hasSize(2);
        assertThat(cookingTips).extracting("id").containsExactlyInAnyOrder(cookingTip1.getId(), cookingTip2.getId());
        assertThat(cookingTips).extracting("category").allMatch(category -> category == CookingTipCategory.PREPARATION);
    }

    @Test
    @DisplayName("요리팁 삭제")
    public void delete() {
        //given
        CookingTip cookingTip = new CookingTip();
        cookingTip.setMember(member);
        cookingTip.setCategory(CookingTipCategory.PREPARATION);
        cookingTip.setTitle("title");
        cookingTip.setContent("content");
        cookingTipRepository.save(cookingTip);

        Long deletedId = cookingTip.getId();

        //when
        cookingTipRepository.delete(cookingTip);

        //then
        Optional<CookingTip> foundCookingTip = cookingTipRepository.findById(deletedId);

        assertThat(foundCookingTip).isEmpty();
    }

}