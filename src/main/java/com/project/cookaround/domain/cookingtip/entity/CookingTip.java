package com.project.cookaround.domain.cookingtip.entity;

import com.project.cookaround.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "cooking_tip")
public class CookingTip {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cooking_tip")
    @SequenceGenerator(name = "seq_cooking_tip", sequenceName = "seq_cooking_tip", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private CookingTipCategory category;

    private String title;
    private String description;
    private String content;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;

        this.viewCount = 0L;
        this.likeCount = 0L;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 조회수 증가
    public void increaseViewCount() {
        this.viewCount++;
    }

    // 좋아요 수 증가
    public void increaseLikeCount() {
        this.likeCount++;
    }

    // 좋아요 수 감소
    public void decreaseLikeCount() {
        this.likeCount--;
    }

}
