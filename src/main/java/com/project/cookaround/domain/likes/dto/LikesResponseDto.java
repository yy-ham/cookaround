package com.project.cookaround.domain.likes.dto;

import com.project.cookaround.domain.likes.entity.Likes;
import com.project.cookaround.domain.likes.entity.LikesContentType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LikesResponseDto {

    private Long id;
    private Long memberId;
    private LikesContentType contentType;
    private Long contentId;


    public static LikesResponseDto fromEntity(Likes like) {
        LikesResponseDto responseDto = new LikesResponseDto();
        responseDto.setId(like.getId());
        responseDto.setMemberId(like.getMember().getId());
        responseDto.setContentType(like.getContentType());
        responseDto.setContentId(like.getContentId());
        return responseDto;
    }

}
