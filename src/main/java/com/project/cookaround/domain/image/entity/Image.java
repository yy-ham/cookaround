package com.project.cookaround.domain.image.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_image")
    @SequenceGenerator(name = "seq_image", sequenceName = "seq_image", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ImageContentType contentType;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
