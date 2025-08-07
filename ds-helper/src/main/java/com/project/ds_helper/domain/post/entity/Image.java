package com.project.ds_helper.domain.post.entity;

import com.project.ds_helper.domain.base.entity.BaseTime;
import com.project.ds_helper.domain.post.util.FileUtil;
import com.project.ds_helper.domain.post.util.S3Util;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_image")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Image extends BaseTime {

    @PrePersist
    public void generatedId(){
        this.id = String.valueOf(UUID.randomUUID());
    }

    /**
     *  식별자 직접 할당
     * **/
    @Id
    @Column(name = "image_id")
    private String id;

    @Column(name = "original_name")
    private String originalName; // 원본 파일명

    @Column(name = "stored_name")
    private String storedName; // 저장 파일명

    @Column(name = "url")
    private String url; // S3 경로

    @Column(name = "size")
    private Long size; // 사이즈

    @Column(name = "content_type")
    private String contentType; // 확장자


}

