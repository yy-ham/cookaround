package com.project.cookaround.domain.likes.service;

import com.project.cookaround.domain.likes.entity.LikesContentType;
import com.project.cookaround.domain.likes.repository.LikesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LikesService {

    private final LikesRepository likesRepository;

    public LikesService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }


    public boolean getLikeByMemberIdAndContentTypeAndContentId(Long memberId, LikesContentType contentType, Long contentId) {
        return likesRepository.existsByMemberIdAndContentTypeAndContentId(memberId, contentType, contentId);
    }
}
