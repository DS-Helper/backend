package com.project.ds_helper.domain.post.dto.request;

import com.project.ds_helper.domain.post.entity.Image;
import com.project.ds_helper.domain.post.entity.Post;
import com.project.ds_helper.domain.post.util.FileUtil;
import com.project.ds_helper.domain.post.util.S3Util;
import com.project.ds_helper.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostReqDto {

    @NotBlank
    private String postId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Size(max = 30)
    private List<MultipartFile> images;


    public Post toUpdatedPost(UpdatePostReqDto dto, Post post,  List<Image> images){
    // 수정 필요
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(post.getUser())
                .images(images)
                .build();
    }


    public static List<Image> toImages(List<MultipartFile> images, User user){
        if(!images.isEmpty()) {
            return images.stream().map( image -> Image.builder()
                    .originalName(image.getOriginalFilename())
                    .storedName(FileUtil.toStoredFilename(image))
                    .url(S3Util.toUrl(image, user))
                    .size(image.getSize())
                    .contentType(image.getContentType())
                    .build()).toList();
        }
        return new ArrayList<Image>();
    }
}