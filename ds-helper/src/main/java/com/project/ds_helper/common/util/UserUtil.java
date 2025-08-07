package com.project.ds_helper.common.util;

import com.project.ds_helper.common.exception.user.UserNotFoundException;
import com.project.ds_helper.domain.user.entity.User;
import com.project.ds_helper.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    /**
     * Aspect로 분리해도 됨
     * authentication에서 userId 추출
     * **/
    public String extractUserId(Authentication authentication){
        String userId = String.valueOf(authentication.getPrincipal());
        log.info("userId : {}", userId);
        return userId;
    }
    
    /**
     * userId 기반 유저 조회
     * **/
    public User findUserById(String userId){
        User user =  userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("유저가 없습니다. 유저 ID : " + userId));
        log.info("유저 조회 완료. 유저 ID : " + userId);
        return user;
    }
}
