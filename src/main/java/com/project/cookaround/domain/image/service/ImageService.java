package com.project.cookaround.domain.image.service;

import com.project.cookaround.domain.image.entity.Image;
import com.project.cookaround.domain.image.entity.ImageContentType;
import com.project.cookaround.domain.image.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
        Path filePath = Paths.get("src/main/resources/static/uploads/cooking-tips");

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
                image.setOriginalName(originalName);
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

    @Transactional
    public void deleteImages(List<Long> deletedImages, ImageContentType contentType, Long contentId) {
        List<Image> images = imageRepository.findByIdIn(deletedImages);
        for (Image image : images) {
            // DB 삭제
            imageRepository.delete(image);

            // 실제 이미지 파일 삭제
            File file = new File(image.getFilePath(), image.getFileName());
            file.delete();
        }
    }

    public List<Image> getImagesByContentTypeAndContentId(ImageContentType contentType, Long contentId) {
        return imageRepository.findByContentTypeAndContentId(contentType, contentId);
    }

    // 요리팁 이미지 조회, 이미지 id 오름차순
    public List<Image> getImagesByContentTypeAndContentIdOrderByIdAsc(ImageContentType contentType, Long contentId) {
        return imageRepository.findByContentTypeAndContentIdOrderByIdAsc(contentType, contentId);
    }

    public Image getFirstImageByContentTypeAndContentId(ImageContentType contentType, Long contentId) {
        return imageRepository.findFirstByContentTypeAndContentIdOrderByIdAsc(contentType, contentId)
                .orElseThrow(() -> new NoSuchElementException("사진을 찾을 수 없습니다."));
    }

}
