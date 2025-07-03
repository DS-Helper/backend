package com.project.ds_helper.domain.user.service;

import com.project.ds_helper.common.util.JwtUtil;
import com.project.ds_helper.domain.user.dto.response.KakaoTokenResponse;
import com.project.ds_helper.domain.user.dto.response.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
//@RequiredArgsConstructor
@Slf4j
public class KakaoAuthService {

        @Value("${kakao.client.id}")
        private String clientId ;
        @Value("${kakao.redirect.uri}")
        private String redirectUri;
        @Value("${kakao.client.secret}")
        String clientSecret;
        private final WebClient kakaoOauthWebClient;
        private final JwtUtil jwtUtil;

        public KakaoAuthService(@Qualifier("kakaoOauthWebClient") WebClient kakaoOauthWebClient, JwtUtil jwtUtil ){
            this.kakaoOauthWebClient = kakaoOauthWebClient;
            this.jwtUtil = jwtUtil;
        }

    public ResponseEntity<?> getAuthToken() {
        return ResponseEntity.ok("https://kauth.kakao.com/oauth/authorize?"
                + "client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code");

//        @GetMapping("/oauth2/authorize/kakao")
//        public void redirectToKakao(HttpServletResponse res) throws IOException {
//            String url = UriComponentsBuilder
//                    .fromUriString("https://kauth.kakao.com/oauth/authorize")
//                    .queryParam("client_id", clientId)
//                    .queryParam("redirect_uri", redirectUri)
//                    .queryParam("response_type", "code")
//                    .toUriString();
//            res.sendRedirect(url);
//        }
    }

    public Mono<ResponseEntity<?>> verifyToken(String code) {
        log.info("kakaoAuthToken : {}", code);
//        return ResponseEntity.ok("success");

        return fetchToken(code)
                .flatMap(tokenResp ->
                        fetchUserInfo(tokenResp.getAccessToken())
                                .map(userResp -> {
                                    Long id = userResp.getId();
                                    String email = userResp.getKakaoAccount().getEmail();
                                    String name = userResp.getKakaoAccount().getName();
                                    String gender = userResp.getKakaoAccount().getGender();
                                    String ageRange = userResp.getKakaoAccount().getAgeRange();
                                    String birthday = userResp.getKakaoAccount().getBirthday();
                                    String birthyear = userResp.getKakaoAccount().getBirthyear();
                                    log.info("id : {}", userResp.getId());
                                    log.info("email : {}", email);
                                    log.info("name : {}", name);
                                    log.info("gender : {}", gender);
                                    log.info("ageRange : {}", ageRange);
                                    log.info("birthday : {}", birthday);
                                    log.info("birthyear : {}", birthyear);

                                    String nickname = userResp.getKakaoAccount().getProfile().getNickname();
                                    String profileImageUrl = userResp.getKakaoAccount().getProfile().getProfileImageUrl();
                                    log.info("nickname : {}", nickname);
                                    log.info("profileImageUrl : {}", profileImageUrl);



//                                    String uuid = userResp.getPartner().getUuid();
//                                    log.info("uuid : {}", uuid);

//                                     (3) UserResponse 에서 이메일·닉네임 추출, DB 회원가입/조회 등 수행
//                                     userService.registerOrGetUser(email, userResp.getKakaoAccount().getProfile().getNickname());
//                                     (4) JWT 생성 or Session 처리
                                    String jwt = jwtUtil.generateAccessToken(email);


                                    // (5) 클라이언트가 쓸 수 있도록 토큰 리턴
                                    return ResponseEntity.ok(Map.of(
                                            "jwt",      jwt,
                                            "kakaoAccount" , userResp.getKakaoAccount(),
                                            "profile",  userResp.getKakaoAccount().getProfile()
                                    ));
                                })
                );


    }

    /**
     * 1) 인가 코드를 받아 액세스 토큰으로 교환
     */
    public Mono<KakaoTokenResponse> fetchToken(String code) {
        return kakaoOauthWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .build()
                )
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("redirect_uri", redirectUri)
                        .with("code", code))
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class);
    }


    /**
     * 2) 받은 액세스 토큰으로 사용자 정보 조회
     */
    public Mono<KakaoUserResponse> fetchUserInfo(String accessToken) { // Mono<KakaoUserResponse>
        return WebClient.create("https://kapi.kakao.com")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/user/me")
                        .build()
                )
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserResponse.class);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /** 응답 데이터 체크 **/
    public Mono<String> checkResponseData(String code) {
        log.info("kakaoAuthToken : {}", code);

//         return fetchToken(code).map(res -> fetchUserInfo(res.getAccessToken()));
         return null;
    }

}