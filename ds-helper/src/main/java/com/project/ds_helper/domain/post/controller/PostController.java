package com.project.ds_helper.domain.post.controller;

import com.project.ds_helper.domain.post.dto.request.CreatePostReqDto;
import com.project.ds_helper.domain.post.dto.request.UpdatePostReqDto;
import com.project.ds_helper.domain.post.dto.response.GetPostResDto;
import com.project.ds_helper.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Tag(name = "게시글")
    @Operation(summary = "유저가 작성한 전체 게시글 조회")
    @GetMapping("")
    public ResponseEntity<List<GetPostResDto>> getAllPostOfUser(Authentication authentication){
        return ResponseEntity.ok(postService.getAllPostOfUser(authentication));
    }

    @Tag(name = "게시글")
    @Operation(summary = "단건 게시글 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResDto> getOnePost(Authentication authentication,
                                                    @PathVariable("postId") String postId){
        return ResponseEntity.ok(postService.getOnePost(authentication, postId));
    }

    @Tag(name = "게시글")
    @Operation(summary = "신규 게시글 생성")
    @PostMapping("")
    public ResponseEntity<?> createPost(Authentication authentication,
                                        @RequestBody @Valid CreatePostReqDto dto) throws IOException {
        postService.createPost(authentication, dto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @Tag(name = "게시글")
    @Operation(summary = "유저가 작성한 기존 게시글 수정")
    @PutMapping("")
    public ResponseEntity<?> updatePost(Authentication authentication,
                                        @RequestBody @Valid UpdatePostReqDto dto){
        postService.updatePost(authentication, dto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Tag(name = "게시글")
    @Operation(summary = "유저가 작성한 기존 게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(Authentication authentication,
                                        @PathVariable("postId") String postId){
        postService.deletePost(authentication, postId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
