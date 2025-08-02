package com.project.cookaround.domain.image.service;

import com.project.cookaround.domain.image.entity.Image;
import com.project.cookaround.domain.image.entity.ImageContentType;
import com.project.cookaround.domain.image.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Transactional
    public void registerImages(List<MultipartFile> images, ImageContentType contentType, Long contentId) {
        List<Image> imageList = new ArrayList<>();
        Path filePath = Paths.get("C:/uploads/cooking-tip");

        for (MultipartFile m : images) {
            if (!m.isEmpty()) {
                String originalName = m.getOriginalFilename();
                String fileName = UUID.randomUUID() + "_" + originalName;
                Path storePath = filePath.resolve(fileName);

                try {
                    m.transferTo(storePath); //IO
                } catch (IOException e) {
                    throw new RuntimeException("사진 저장을 실패했습니다.");
                }

                Image image = new Image();
                image.setContentType(contentType);
                image.setContentId(contentId);
                image.setOriginalName(m.getOriginalFilename());
                image.setFileName(fileName);
                image.setFilePath(filePath.toString());
                image.setFileSize(m.getSize());

                imageList.add(image);
            }
        }

        for (Image i : imageList) {
            imageRepository.save(i);
        }
    }

}
