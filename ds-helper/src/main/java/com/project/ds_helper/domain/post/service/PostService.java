package com.project.ds_helper.domain.post.service;

import com.project.ds_helper.common.exception.user.UserNotFoundException;
import com.project.ds_helper.common.util.PostUtil;
import com.project.ds_helper.common.util.UserUtil;
import com.project.ds_helper.domain.post.dto.request.CreatePostReqDto;
import com.project.ds_helper.domain.post.dto.request.UpdatePostReqDto;
import com.project.ds_helper.domain.post.dto.response.GetPostResDto;
import com.project.ds_helper.domain.post.entity.Image;
import com.project.ds_helper.domain.post.entity.Post;
import com.project.ds_helper.domain.post.repository.ImageRepository;
import com.project.ds_helper.domain.post.repository.PostRepository;
import com.project.ds_helper.domain.post.util.ImageUtil;
import com.project.ds_helper.domain.post.util.S3Util;
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
        private final ImageRepository imageRepository;
        private final UserUtil userUtil;
        private final ImageUtil imageUtil;
        private final PostUtil postUtil;
        private final S3Util s3Util;

        /**
         * 유저의 모든 게시글 조회
         * **/
        public List<GetPostResDto> getAllPostOfUser(Authentication authentication) {

            // 유저 id 추출
            String userId = userUtil.extractUserId(authentication);

            // 게시글 리스트 조회
            List<GetPostResDto> posts = postRepository.findAllByUser_Id(userId);
            log.info("게시글 리스트 조회");

            return posts;
        }

        /**
         * 단건 게시물 조회
         * **/
        public GetPostResDto getOnePost(Authentication authentication, String postId) {

            // 게시글 조회
            GetPostResDto responseDto = GetPostResDto.toDto(postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시물 조회 실패, 게시물 ID : " + postId)));
            log.info("게시글 조회 완료");

            return responseDto;
        }

        /**
         * 신규 게시글 작성
         * 반환 타입 수정 필요
         * **/
        public void createPost(Authentication authentication, CreatePostReqDto dto) {

            // 유저 id 추출
            String userId = userUtil.extractUserId(authentication);
            
            // 유저 조회
            User user = userUtil.findUserById(userId);
    
            // 이미지 엔티티 빌드
            List<Image> images = imageUtil.toImages(dto.getImages());
            log.info("이미지 엔티티 리스트 빌드 완료");

            // 이미지 저장 (CascadeType.ALL 이지만, 안정성을 위해 직접 저장)
            List<Image> savedImages = imageRepository.saveAll(images);
        
            // 게시글 엔티티 빌드
            Post post = dto.toPost(dto, user, savedImages);
            log.info("게시글 엔티티 빌드 완료");
            
            // 게시글 저장
            postRepository.save(post);
            log.info("게시글 저장");
        }
        

    
        /**
         * 게시글 수정
         * 이미지 리스트 수정 기능 확인
         * Dto 수정 필요 (프론트측 답변 오면 )
         * **/
        public void updatePost(Authentication authentication, UpdatePostReqDto dto) {

            String userId = userUtil.extractUserId(authentication);
            // 게시글 작성자가 아니면 예외
            // 삭제, 신규 추가 된 이미지 처리
            // s3 이미지 처리

        }
        /**
         * 게시글 삭제 
         * S3 이미지 삭제
         *
         * **/
        public void deletePost(Authentication authentication, String postId) {
            
            // 유저 id 조회
            String userId = userUtil.extractUserId(authentication);
        
            // 유저 조회
            User user = userUtil.findUserById(userId);
    
            // 게시글 조회
            Post post = postUtil.findPostById(postId);

            // 게시글 작성자가 아니면 삭제 불가능 예외 처리
            if(!userId.equals(post.getUser().getId())){
                throw new IllegalArgumentException("게시글 작성자가 아닙니다. 유저 ID : " + userId + "게시글 ID : " + postId);
            }

            // 게시글 삭제 전 삭제할 이미지 경로 확보를 위해 이미지 리스트 변수에 할당
            List<String> imageUrlsToDelete = post.getImages().stream().map(image -> s3Util.toS3Url(image.getUrl())).toList();

            // 게시글 삭제
            postUtil.deletePostById(postId);

            // 이미지 삭제
            s3Util.deleteImages(imageUrlsToDelete);
        }
    

}
