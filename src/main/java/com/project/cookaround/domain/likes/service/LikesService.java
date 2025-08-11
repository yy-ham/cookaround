package com.project.cookaround.domain.likes.service;

import com.project.cookaround.domain.cookingtip.entity.CookingTip;
import com.project.cookaround.domain.cookingtip.repository.CookingTipRepository;
import com.project.cookaround.domain.likes.entity.Likes;
import com.project.cookaround.domain.likes.entity.LikesContentType;
import com.project.cookaround.domain.likes.repository.LikesRepository;
import com.project.cookaround.domain.member.entity.Member;
import com.project.cookaround.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final CookingTipRepository cookingTipRepository;

    public LikesService(LikesRepository likesRepository, MemberRepository memberRepository, CookingTipRepository cookingTipRepository) {
        this.likesRepository = likesRepository;
        this.memberRepository = memberRepository;
        this.cookingTipRepository = cookingTipRepository;
    }

    public Likes getLikeById(Long id) {
        return likesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("좋아요를 찾을 수 없습니다."));
    }

    public Likes getLikeByMemberIdAndContentTypeAndContentId(Long memberId, LikesContentType contentType, Long contentId) {
        return likesRepository.findByMemberIdAndContentTypeAndContentId(memberId, contentType, contentId)
                .orElseThrow(() -> new NoSuchElementException("좋아요를 찾을 수 없습니다."));
    }

    public boolean existsLikeByMemberIdAndContentTypeAndContentId(Long memberId, LikesContentType contentType, Long contentId) {
        return likesRepository.existsByMemberIdAndContentTypeAndContentId(memberId, contentType, contentId);
    }

    @Transactional
    public Long registerLike(Long memberId, Likes like) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        CookingTip cookingTip = cookingTipRepository.findById(like.getContentId())
                .orElseThrow(() -> new NoSuchElementException("요리팁을 찾을 수 없습니다."));

        like.setMember(member);
        likesRepository.save(like);

        cookingTip.increaseLikeCount(); // 좋아요 수 증가

        return cookingTip.getLikeCount();
    }

    @Transactional
    public void deleteLike(Long id) {
        Likes like = likesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("좋아요를 찾을 수 없습니다."));
        likesRepository.delete(like);
    }
}
