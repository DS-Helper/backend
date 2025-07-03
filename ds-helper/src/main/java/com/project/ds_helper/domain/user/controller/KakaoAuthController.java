package com.project.ds_helper.domain.user.controller;

import com.project.ds_helper.domain.user.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/kakao")
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;
    
    // XHR 요청 시 직접 redirect 가능
    @GetMapping("/auth-token")
    public ResponseEntity<?> getAuthToken(){
        return kakaoAuthService.getAuthToken();
    }

    @GetMapping("/verify-token")
    public Mono<ResponseEntity<?>> verifyToken(@RequestParam("code") String kakaoAuthToken){
        return kakaoAuthService.verifyToken(kakaoAuthToken);
    }

    @GetMapping("/checkResponse")
    public Mono<String> checkResponse(@RequestParam("code") String kakaoAuthToken){ // ResponseEntity<?>
        return kakaoAuthService.checkResponseData(kakaoAuthToken);
    }

}
