package com.project.ds_helper.domain.post.util;

import com.amazonaws.services.cloudformation.model.OperationInProgressException;
import com.project.ds_helper.domain.post.entity.Image;
import com.project.ds_helper.domain.user.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectDeletedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Util {

    @Value("${cloud.aws.credentials.access-key}")
    private static String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private static String secretKey;
    @Value("${cloud.aws.region.static}")
    private static String region;
    @Value("${s3.bucket}")
    private static String bucket;

    private S3Client s3Client;
    private final ImageUtil imageUtil;

    // 생성 시 설정 세팅
    @PostConstruct
    public void init() {
        s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    public String upload(MultipartFile file, User user) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 없습니다. 요청 유저 ID : " + user.getId());
        }

        String filename = toUrl(file);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(filename)
                .contentType(file.getContentType())
                .acl("public-read") // 공개 버킷이면
                .build();

        s3Client.putObject(request, software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + filename;
    }

    public void deleteImages(List<Image> images){
        if (images.isEmpty()) {
            log.info("삭제할 이미지가 없습니다.");
            throw new IllegalArgumentException("삭제할 이미지가 없습니다.");
        }

        if(images.size() == 1){
            String imageUrl = images.getFirst().getUrl();

            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(imageUrl)
                    .build();

            if(!s3Client.deleteObject(request).deleteMarker()){
                throw new OperationInProgressException("이미지 삭제 실패. 이미지 URL : " + toS3Url(imageUrl) );
            };
        }






    }


    public String toS3Url(String imageUrl){
        if(imageUrl.isEmpty()){
            throw new IllegalArgumentException("imageUrl 없습니다.");
        }
        log.info("imageUrl : {}", imageUrl);
        return "https://" + bucket + ".s3." + region + ".amazonaws.com" + imageUrl;
    }
}
