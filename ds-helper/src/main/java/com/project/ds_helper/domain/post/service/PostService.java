package com.project.ds_helper.domain.post.service;

import com.project.ds_helper.common.exception.user.UserNotFoundException;
import com.project.ds_helper.common.util.UserUtil;
import com.project.ds_helper.domain.post.dto.request.CreatePostReqDto;
import com.project.ds_helper.domain.post.dto.request.UpdatePostReqDto;
import com.project.ds_helper.domain.post.dto.response.GetPostResDto;
import com.project.ds_helper.domain.post.entity.Image;
import com.project.ds_helper.domain.post.entity.Post;
import com.project.ds_helper.domain.post.repository.PostRepository;
import com.project.ds_helper.domain.post.util.ImageUtil;
import com.project.ds_helper.domain.user.entity.User;
import com.project.ds_helper.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

        private final PostRepository postRepository;
        private final UserRepository userRepository;
        private final UserUtil userUtil;
        private final ImageUtil imageUtil;

        
        /**
         * 신규 게시글 작성
         * 반환 타입 수정 필요
         * **/
        public void createPost(Authentication authentication, CreatePostReqDto dto) {

            // 유저 id 추출
            String userId = userUtil.extractUserId(authentication);

            User user = userUtil.findUserById(userId);

            List<Image> images = imageUtil.toImages(dto.getImages());
            log.info("이미지 엔티티 리스트 빌드 완료");

            Post post = dto.toPost(dto, user, images);
            log.info("게시글 엔티티 빌드 완료");
            
            postRepository.save(post);
            log.info("게시글 저장");
        }
        
        /**
         * 단건 게시물 조회
         * **/
        public GetPostResDto getOnePost(Authentication authentication, String postId) {

            // 유저 id 추출
            userUtil.extractUserId(authentication);
            
            // 게시글 조회
            Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시물 조회 실패, 게시물 ID : " + postId));
            log.info("게시글 조회 완료");

            return GetPostResDto.toDto(post);
        }
    
        /**
         * 게시글 수정
         * 이미지 리스트 수정 기능 확인
         * **/
        public void updatePost(Authentication authentication, UpdatePostReqDto dto) {
            
        }
}
