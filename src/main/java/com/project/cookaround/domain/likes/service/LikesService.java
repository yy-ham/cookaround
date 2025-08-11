package com.project.cookaround.domain.likes.service;

import com.project.cookaround.domain.likes.entity.Likes;
import com.project.cookaround.domain.likes.entity.LikesContentType;
import com.project.cookaround.domain.likes.repository.LikesRepository;
import com.project.cookaround.domain.member.entity.Member;
import com.project.cookaround.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;

    public LikesService(LikesRepository likesRepository, MemberRepository memberRepository) {
        this.likesRepository = likesRepository;
        this.memberRepository = memberRepository;
    }


    public boolean getLikeByMemberIdAndContentTypeAndContentId(Long memberId, LikesContentType contentType, Long contentId) {
        return likesRepository.existsByMemberIdAndContentTypeAndContentId(memberId, contentType, contentId);
    }

    @Transactional
    public Long registerLike(Long memberId, Likes like) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        like.setMember(member);
        likesRepository.save(like);

        return like.getId();
    }
}
