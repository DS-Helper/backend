package com.project.ds_helper.domain.post.controller;

import com.project.ds_helper.domain.post.dto.request.CreatePostReqDto;
import com.project.ds_helper.domain.post.dto.request.UpdatePostReqDto;
import com.project.ds_helper.domain.post.dto.response.GetAllPostOfUserResDto;
import com.project.ds_helper.domain.post.dto.response.GetPostResDto;
import com.project.ds_helper.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    
    /**
     * 도와드린 이야기
     *
     * **/

    private final PostService postService;

    @Tag(name = "게시글")
    @Operation(summary = "유저가 작성한 전체 게시글 조회")
    @ApiResponse(responseCode = "200", useReturnTypeSchema = true)
    @GetMapping("")
    public ResponseEntity<GetAllPostOfUserResDto> getAllPostOfUser(Authentication authentication,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   @RequestParam(defaultValue = "desc") String sort,
                                                                   @RequestParam(defaultValue = "createAt") String sortBy){
        return ResponseEntity.ok(postService.getAllPostOfUser(authentication, page, size, sort, sortBy));
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
