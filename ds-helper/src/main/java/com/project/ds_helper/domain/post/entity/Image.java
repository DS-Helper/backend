package com.project.ds_helper.domain.post.entity;

import com.project.ds_helper.domain.base.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_image")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Image extends BaseTime {

    /**
     *  식별자 직접 할당
     * **/
    @Id
    @Column(name = "image_id")
    private String id;

    @Column(name = "original_name")
    private String originalName; // 업로드 시 파일명

    @Column(name = "stored_name")
    private String storedName; // 실제 서버 저장 파일명

    @Column(name = "url")
    private String url; // S3, 서버 경로 등

    @Column(name = "size")
    private Long size; // 사이즈

    @Column(name = "content_type")
    private String contentType; // 확장자

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {})
    @JoinColumn(name = "post_id")
    private Post post;

















}

