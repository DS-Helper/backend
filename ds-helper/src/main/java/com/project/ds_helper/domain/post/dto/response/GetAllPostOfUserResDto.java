package com.project.ds_helper.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ds_helper.common.dto.response.PageResDto;
import com.project.ds_helper.domain.post.entity.Image;
import com.project.ds_helper.domain.post.entity.Post;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public record GetAllPostOfUserResDto(
        List<GetPostResDto> posts,
        PageResDto page
) {
    public static PageResDto toPage(Page<Post> posts){
        return new PageResDto(posts.getNumber(), posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isFirst(), posts.isLast(), posts.hasNext(), posts.hasPrevious(), posts.getPageable().getSort());
    }

    public static GetPostResDto toDto(Post post){
        return GetPostResDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writerName(post.getUser().getName())
                .imageUrls(post.getImages().stream().map(Image::getUrl).toList())
                .build();
    }

    public static GetAllPostOfUserResDto toDtoList(Page<Post> posts){
        if(posts.isEmpty()){
            return new GetAllPostOfUserResDto(new ArrayList<>(), new PageResDto(0,0,0L,0,false,false,false,false, posts.getSort()));
        }
        return new GetAllPostOfUserResDto(posts.stream().map(GetPostResDto::toDto).toList(), toPage(posts));
    }


}
