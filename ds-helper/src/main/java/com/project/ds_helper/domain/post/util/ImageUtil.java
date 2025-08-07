package com.project.ds_helper.domain.post.util;

import com.project.ds_helper.domain.post.entity.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class ImageUtil {

    private final S3Util s3Util;
    private final ImageUtil imageUtil;

    /**
     * 이미지 엔티티 빌드
     * **/
    public List<Image> toImages(List<MultipartFile> images){
        List<Image> result = new ArrayList<>();
        if(!images.isEmpty()) {
            images.stream().peek( image -> result.add(Image.builder()
                    .originalName(image.getOriginalFilename())
                    .storedName(FileUtil.toStoredFilename(image))
                    .url(s3Util.toS3Url(imageUtil.toImageUrl(image)))
                    .size(image.getSize())
                    .contentType(image.getContentType())
                    .build()));
        }
        return result;
    }

    public String toImageUrl(MultipartFile image){
            if(image.isEmpty()){
                throw new IllegalArgumentException("파일이 없습니다.");
            }
            return "/image/"
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                    + "_" + URLEncoder.encode(Objects.requireNonNull(image.getOriginalFilename()), StandardCharsets.UTF_8);
    }


}
