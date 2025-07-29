package com.project.cookaround.domain.cookingtip.service;

import com.project.cookaround.domain.cookingtip.entity.CookingTip;
import com.project.cookaround.domain.cookingtip.entity.CookingTipCategory;
import com.project.cookaround.domain.cookingtip.repository.CookingTipRepository;
import com.project.cookaround.domain.member.entity.Member;
import com.project.cookaround.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class CookingTipService {

    private final CookingTipRepository cookingTipRepository;
    private final MemberRepository memberRepository;

    public CookingTipService(CookingTipRepository cookingTipRepository, MemberRepository memberRepository) {
        this.cookingTipRepository = cookingTipRepository;
        this.memberRepository = memberRepository;
    }


    // 요리팁 조회 - 전체
    public List<CookingTip> getAllCookingTips() {
        return cookingTipRepository.findAll();
    }

    // 요리팁 조회 - 카테고리
    public List<CookingTip> getCookingTipsByCategory(CookingTipCategory category) {
        return cookingTipRepository.findByCategory(category);
    }

    // 요리팁 조회 - 상세
    public CookingTip getCookingTipDetail(Long id) {
        return cookingTipRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("요리팁을 찾을 수 없습니다."));
    }

    // 요리팁 등록
    @Transactional
    public Long registerCookingTip(Long memberId, CookingTip cookingTip) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        cookingTip.setMember(member);
        cookingTipRepository.save(cookingTip);

        return cookingTip.getId();
    }

    // 요리팁 수정
    @Transactional
    public Long updateCookingTip(Long memberId, CookingTip cookingTip) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        cookingTip.setMember(member);
        if (cookingTip.getId() != null) {
            cookingTipRepository.save(cookingTip);
        }

        return cookingTip.getId();
    }

    // 요리팁 삭제
    @Transactional
    public void deleteCookingTip(CookingTip cookingTip) {
        cookingTipRepository.delete(cookingTip);
    }

}
