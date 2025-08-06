package com.project.ds_helper.domain.post.service;

import com.project.ds_helper.common.exception.user.UserNotFoundException;
import com.project.ds_helper.domain.post.dto.request.CreatePostReqDto;
import com.project.ds_helper.domain.post.dto.response.GetPostResDto;
import com.project.ds_helper.domain.post.entity.Post;
import com.project.ds_helper.domain.post.repository.PostRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

        private final PostRepository postRepository;
        private final UserRepository userRepository;

        
        /**
         * 신규 게시글 작성
         * **/
        public ResponseEntity<?> createPost(Authentication authentication, CreatePostReqDto dto) {

                return null;
        }
        
        /**
         * 단건 게시물 조회
         * **/
        public GetPostResDto getOnePost(Authentication authentication, String postId) {

                String userId = authentication.getPrincipal().toString();
                log.info("userId : {}", userId);

               User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다. 유저 ID : " + userId));
               log.info("유저 조회 완료");

               Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시물 조회 실패, 게시물 ID : " + postId));
               log.info("게시글 조회 완료");

               return GetPostResDto.toDto(post);
        }
}
