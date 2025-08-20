package com.project.ds_helper.domain.user.controller;

import com.project.ds_helper.domain.reservation.dto.response.GetPersonalReservationResDto;
import com.project.ds_helper.domain.user.service.KakaoOauthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/kakao")
public class KakaoAuthController {

    private final KakaoOauthService kakaoOauthService;
    
    // XHR 요청 시 직접 redirect 가능
    @Tag(name = "유저")
    @Operation(summary = "카카오 회원가입 & 로그인 URL 조회 API")
    @GetMapping("/login-url")
    public ResponseEntity<String>getKakaoLoginUrl(){
        return ResponseEntity.ofNullable(kakaoOauthService.getKakaoLoginUrl());
    }

    @Tag(name = "유저")
    @Operation(summary = "카카오 회원가입 & 로그인 API")
    @GetMapping("/login")
    public ResponseEntity<Void> JoinWithKakaoOauthToken(@RequestParam("code") String kakaoAuthToken,
                                                                                 HttpServletResponse httpServletResponse) throws InterruptedException {
        kakaoOauthService.JoinWithKakaoOauthToken(kakaoAuthToken, httpServletResponse);
        return ResponseEntity.ofNullable(null);
    }
}
