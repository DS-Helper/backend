package com.project.ds_helper.domain.post.dto.request;

import com.project.ds_helper.domain.post.entity.Post;
import com.project.ds_helper.domain.user.entity.User;
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
public class CreatePostReqDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Size(max = 10)
    private List<MultipartFile> images;

    public static Post toPost(CreatePostReqDto dto, User user){
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setWriter(user);
        post.setImagePath(dto.getImages().stream().map(MultipartFile::getOriginalFilename).toList());
        return post;
    }
}
