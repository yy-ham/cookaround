package com.project.cookaround.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter // 모든 필드에 대한 getter 메서드 자동 생성
@Setter
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자를 자동 생성
public class Recipe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId; // java에서 카멜표기법을 권장한다고 해서 member_id -> memberId로 변경

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private RecipeCategory category;

    private String title;
    private String description;
    private Integer difficulty;

    @Column(name = "cooking_time")
    private Integer cookingTime;

    private Integer serving;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /*
    보통 createdAt 과 updatedAt 은 DB에서 자동 생성 또는 업데이트하거나,
    JPA의 @PrePersist, @PreUpdate 콜백으로 관리합니다.

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }*/


}
