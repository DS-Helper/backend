package com.project.ds_helper.common.util;

import com.project.ds_helper.domain.post.entity.Image;
import com.project.ds_helper.domain.post.repository.ImageRepository;
import com.project.ds_helper.domain.post.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;

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

    }
    
}
