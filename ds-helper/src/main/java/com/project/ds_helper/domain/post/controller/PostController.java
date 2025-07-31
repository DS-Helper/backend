package com.project.ds_helper.domain.post.controller;

import com.project.ds_helper.domain.post.dto.request.CreatePostReqDto;
import com.project.ds_helper.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<?> createPost(@AuthenticationPrincipal Long userId,
                                        @Valid CreatePostReqDto dto){
        return postService.createPost(userId, dto);
    }
}
