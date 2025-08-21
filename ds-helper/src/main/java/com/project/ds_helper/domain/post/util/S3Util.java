package com.project.ds_helper.domain.post.util;

import com.amazonaws.services.cloudformation.model.OperationInProgressException;
import com.project.ds_helper.common.dto.request.S3ImageUploadReqDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Util {

    @Value("${cloud.aws.credentials.access-key}")
    private static String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private static String secretKey;
//    @Value("${cloud.aws.region.static}")
//    private static String region;
    @Value("${s3.bucket}")
    private static String bucket;

    private S3Client s3Client;

    // 생성 시 설정 세팅
    @PostConstruct
    public void init() {
        s3Client = S3Client.builder()
                .region(Region.AP_SOUTHEAST_2)
                .credentialsProvider(DefaultCredentialsProvider.create())
//                .credentialsProvider(
//                        StaticCredentialsProvider.create(
//                                AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    public void uploadImage(S3ImageUploadReqDto dto ) throws IOException {

        String storedFilename = dto.getStoredFilename();
        String contentType = dto.getContentType();
        byte[] bytes = dto.getBytes();
        log.info("storedFilename : {}, contentType : {}, bytes : {}", storedFilename, contentType, bytes);


        if (storedFilename.isBlank() || bytes == null ) {
            log.info("storedFilename : {}, contentType : {}, bytes : {}", storedFilename, contentType, bytes);
            throw new IllegalArgumentException("파일이 없습니다.");
        }

        String imageUrl = toS3Url(storedFilename);
        log.info("imageUrl : {}", imageUrl);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(imageUrl)
                .contentType(contentType)
                .acl("public-read") // 공개 버킷이면
                .build();

         if(!s3Client.putObject(request, software.amazon.awssdk.core.sync.RequestBody.fromBytes(bytes)).sdkHttpResponse().isSuccessful()){
             throw new IOException("S3 이미지 저장 실패");
         }
    }
    
    /**
     * 복수개 이미지 업로드
     * **/

    public List<String> uploadImages(List<S3ImageUploadReqDto> imageFiles) throws IOException {
        // 이미지 여부 확인
        if (imageFiles.isEmpty()) {
            throw new IllegalArgumentException("파일이 없습니다.");
        }

        List<String> urls = new ArrayList<>();
        // s3 이미지 요청 빌드
        for(S3ImageUploadReqDto imageFile : imageFiles){
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(toS3Url(imageFile.getStoredFilename()))
                    .contentType(imageFile.getContentType())
                    .acl("public-read") // 공개 버킷이면
                    .build();
            // 이미지 업로드
            if(!s3Client.putObject(request, software.amazon.awssdk.core.sync.RequestBody.fromBytes(imageFile.getBytes())).sdkHttpResponse().isSuccessful()){
                throw new IOException("S3 이미지 저장 실패");
            }
            // 이미지 url
            urls.add(request.key());
        }

        return urls;
    }


    public void deleteImage(String imageUrl){
        if (imageUrl.isEmpty()) {
            log.info("삭제할 이미지 경로가 없습니다.");
            throw new IllegalArgumentException("삭제할 이미지 경로가 없습니다.");
        }

            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(imageUrl)
                    .build();

            if(!s3Client.deleteObject(request).deleteMarker()){
                throw new OperationInProgressException("이미지 삭제 실패. 이미지 URL : " + toS3Url(imageUrl) );
            };
    }

    public void deleteImages(List<String> imageUrls){
        if(imageUrls.isEmpty()){
            log.info("삭제할 이미지 경로가 없습니다.");
        }

        List<ObjectIdentifier> objectIdentifiers = imageUrls.stream().map( imageUrl -> ObjectIdentifier.builder().key(imageUrl).build()).toList();

        Delete delete = Delete.builder().objects(objectIdentifiers).build();

        DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder().delete(delete).build();

        s3Client.deleteObjects(deleteObjectsRequest);
    }




    public String toS3Url(String imageUrl){
        if(imageUrl.isEmpty()){
            throw new IllegalArgumentException("imageUrl 없습니다.");
        }
        log.info("imageUrl : {}", imageUrl);
        return "https://" + bucket + ".s3." + Region.AP_SOUTHEAST_2 + ".amazonaws.com" + imageUrl;
    }
}
