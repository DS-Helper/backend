package com.project.ds_helper.domain.post.util;

import com.project.ds_helper.domain.post.entity.Image;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImageUtil {

    /**
     * 이미지 엔티티 빌드
     * **/
    public List<Image> toImages(List<MultipartFile> images){
        if(!images.isEmpty()) {
            return images.stream().map( image -> Image.builder()
                    .originalName(image.getOriginalFilename())
                    .storedName(FileUtil.toStoredFilename(image))
                    .url(S3Util.toUrl(image))
                    .size(image.getSize())
                    .contentType(image.getContentType())
                    .build()).toList();
        }
        return new ArrayList<Image>();
    }
}
