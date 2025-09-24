package com.project.cookaround.domain.image.dto;

import com.project.cookaround.domain.image.entity.Image;
import com.project.cookaround.domain.image.entity.ImageContentType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImageResponseDto {

    private Long id;
    private ImageContentType contentType;
    private Long contentId;
    private String fileName;


    // Entity -> Dto 변환
    public static ImageResponseDto fromEntity(Image image) {
        ImageResponseDto imageResponseDto = new ImageResponseDto();
        imageResponseDto.setId(image.getId());
        imageResponseDto.setContentType(image.getContentType());
        imageResponseDto.setContentId(image.getContentId());
        imageResponseDto.setFileName(image.getFileName());
        return imageResponseDto;
    }

}
