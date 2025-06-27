package com.project.cookaround.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RecipeDto {

    private Long id;
    private Long memberId;
    private String category;
    private String title;
    private String description;
    private Integer difficulty;
    private Integer cookingTime;
    private Integer serving;
    private Long viewCount;
    private Long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String memberProfile;   // 회원 프로필사진
    private String loginId;
    private Double avgRating;       // 후기 평균 별점
    private Long reviewCount;       // 후기 개수
}
