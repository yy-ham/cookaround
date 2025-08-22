package com.project.cookaround.domain.cookingtip.service;

import com.project.cookaround.domain.cookingtip.entity.CookingTip;
import com.project.cookaround.domain.cookingtip.entity.CookingTipCategory;
import com.project.cookaround.domain.cookingtip.repository.CookingTipRepository;
import com.project.cookaround.domain.image.entity.ImageContentType;
import com.project.cookaround.domain.image.repository.ImageRepository;
import com.project.cookaround.domain.image.service.ImageService;
import com.project.cookaround.domain.likes.entity.Likes;
import com.project.cookaround.domain.likes.entity.LikesContentType;
import com.project.cookaround.domain.likes.repository.LikesRepository;
import com.project.cookaround.domain.member.entity.Member;
import com.project.cookaround.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CookingTipService {

    private final CookingTipRepository cookingTipRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final LikesRepository likesRepository;
    private final ImageService imageService;

    public CookingTipService(CookingTipRepository cookingTipRepository, MemberRepository memberRepository,
                             ImageRepository imageRepository, LikesRepository likesRepository, ImageService imageService) {
        this.cookingTipRepository = cookingTipRepository;
        this.memberRepository = memberRepository;
        this.imageRepository = imageRepository;
        this.likesRepository = likesRepository;
        this.imageService = imageService;
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
    @Transactional
    public CookingTip getCookingTipDetail(Long id) {
        CookingTip cookingTip = cookingTipRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("요리팁을 찾을 수 없습니다."));

        cookingTip.increaseViewCount();

        return cookingTip;
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
        CookingTip foundCookingTip = cookingTipRepository.findById(cookingTip.getId())
                .orElseThrow(() -> new NoSuchElementException("요리팁을 조회할 수 없습니다."));

        foundCookingTip.setCategory(cookingTip.getCategory());
        foundCookingTip.setTitle(cookingTip.getTitle());
        foundCookingTip.setDescription(cookingTip.getDescription());
        foundCookingTip.setContent(cookingTip.getContent());

        return foundCookingTip.getId();
    }

    // 요리팁 삭제
    @Transactional
    public boolean deleteCookingTip(Long cookingTipId) {
        // 요리팁 삭제
        CookingTip cookingTip = cookingTipRepository.findById(cookingTipId)
                .orElseThrow(() -> new NoSuchElementException("요리팁을 조회할 수 없습니다."));
        cookingTipRepository.delete(cookingTip);

        // 이미지 삭제
        List<Long> imageIds = imageRepository.findIdsByContentTypeAndContentId(ImageContentType.COOKINGTIP, cookingTipId);
        imageService.deleteImages(imageIds, ImageContentType.COOKINGTIP, cookingTipId);

        // 좋아요 삭제
        List<Likes> likes = likesRepository.findByContentTypeAndContentId(LikesContentType.COOKINGTIP, cookingTipId);
        for (Likes like : likes) {
            likesRepository.delete(like);
        }

        // 삭제 확인
        Optional<CookingTip> deletedCookingTip = cookingTipRepository.findById(cookingTipId);
        return deletedCookingTip.isEmpty();
    }

}
