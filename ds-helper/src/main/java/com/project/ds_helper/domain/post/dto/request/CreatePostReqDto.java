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
public class CreatePostReqDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Size(max = 30)
    private List<MultipartFile> images;

    public Post toPost(CreatePostReqDto dto, User user, List<Image> images){
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .images(images)
                .build();
    }


}
