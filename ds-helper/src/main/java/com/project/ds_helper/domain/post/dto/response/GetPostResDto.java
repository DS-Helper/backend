package com.project.ds_helper.domain.post.dto.response;

import com.project.ds_helper.domain.post.entity.Image;
import com.project.ds_helper.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPostResDto {

    private String postId;

    private String title;

    private String content;

    private String writerName;

    private List<String> imageUrls;

    public static GetPostResDto toDto(Post post){
        return GetPostResDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writerName(post.getUser().getName())
                .imageUrls(post.getImages().stream().map(Image::getUrl).toList())
                .build();
    }

    public static List<GetPostResDto> toDtoList(List<Post> posts){
        if(posts.isEmpty()){
            return new ArrayList<GetPostResDto>();
        }
        return posts.stream().map(GetPostResDto::toDto).toList();
    }

}
