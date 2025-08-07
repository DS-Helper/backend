package com.project.ds_helper.common.util;

import com.project.ds_helper.domain.post.entity.Post;
import com.project.ds_helper.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostUtil {

    private final PostRepository postRepository;

    public Post findPostById(String postId){
        Post post = postRepository.findById(postId).orElseThrow( () -> new IllegalArgumentException("게시물 조회 실패. 게시물 ID : " + postId ));
        log.info("게시물 조회 완료. 게시물 ID : " + postId);
        return post;
    }

    public void deletePostById(String postId){
        postRepository.deleteById(postId);
        log.info("게시물 삭제 완료. 게시물 ID : " + postId);
    }

}
