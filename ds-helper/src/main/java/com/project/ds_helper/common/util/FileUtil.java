package com.project.ds_helper.common.util;

import com.project.ds_helper.domain.post.entity.Image;
import com.project.ds_helper.domain.post.repository.ImageRepository;
import com.project.ds_helper.domain.post.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileUtil {

    private final ImageRepository imageRepository;
    private final S3Util s3Util;

    /**
     * Image 엔티티 삭제
     * S3에 저장된 Image 삭제
     * Post의 이미지 리스트 갱신
     */
    public static boolean deleteImage(Image image){
         return false;
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
