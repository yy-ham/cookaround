package com.project.cookaround.domain.likes.dto;

import com.project.cookaround.domain.likes.entity.Likes;
import com.project.cookaround.domain.likes.entity.LikesContentType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LikesRequestDto {

    private LikesContentType contentType;
    private Long contentId;


    public Likes toEntity() {
        Likes like = new Likes();
        like.setContentType(this.contentType);
        like.setContentId(this.contentId);
        return like;
    }
}
