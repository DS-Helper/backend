package com.project.ds_helper.domain.post.controller;

import com.project.ds_helper.domain.post.dto.request.CreatePostReqDto;
import com.project.ds_helper.domain.post.dto.response.GetPostResDto;
import com.project.ds_helper.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResDto> getOnePost(Authentication authentication,
                                                    @PathVariable("postId") String postId){
        return ResponseEntity.ok(postService.getOnePost(authentication, postId));
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(Authentication authentication,
                                        @RequestBody @Valid CreatePostReqDto dto){
        return postService.createPost(authentication, dto);
    }
}
