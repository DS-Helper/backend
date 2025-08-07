package com.project.ds_helper.domain.post.dto.request;

import com.project.ds_helper.domain.post.entity.Image;
import com.project.ds_helper.domain.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
}