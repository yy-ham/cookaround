package com.project.cookaround.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member")
    @SequenceGenerator(name = "seq_member", sequenceName = "seq_member", allocationSize = 1)
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    private String password;

    private String email;

    private String profile;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(name = "join_at")
    private LocalDateTime joinAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;


    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.joinAt = now;
        this.updatedAt = now;
        this.lastLoginAt = now;

        this.profile = "default.jpg";
        this.status = MemberStatus.ACTIVE;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}