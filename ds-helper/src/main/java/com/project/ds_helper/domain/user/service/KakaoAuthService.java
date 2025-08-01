package com.project.ds_helper.domain.user.service;

import com.project.ds_helper.common.util.CookieUtil;
import com.project.ds_helper.common.util.JwtUtil;
import com.project.ds_helper.domain.user.dto.response.KakaoTokenResponse;
import com.project.ds_helper.domain.user.dto.response.KakaoUserResponse;
import com.project.ds_helper.domain.user.entity.KakaoOauth;
import com.project.ds_helper.domain.user.entity.User;
import com.project.ds_helper.domain.user.enums.UserRole;
import com.project.ds_helper.domain.user.repository.KakaoOauthRepository;
import com.project.ds_helper.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
        private final RestTemplate restTemplate;
        private final JwtUtil jwtUtil;
        private final KakaoOauthRepository kakaoOauthRepository;
        private final UserRepository userRepository;

        public KakaoAuthService(@Qualifier("kakaoOauthWebClient") WebClient kakaoOauthWebClient,
//                                @Qualifier("kakaoOauthRestTemplate") RestTemplate restTemplate,
                                RestTemplate restTemplate,
                                JwtUtil jwtUtil,
                                KakaoOauthRepository kakaoOauthRepository,
                                UserRepository userRepository){
            this.kakaoOauthWebClient = kakaoOauthWebClient;
            this.restTemplate = restTemplate;
            this.jwtUtil = jwtUtil;
            this.kakaoOauthRepository = kakaoOauthRepository;
            this.userRepository = userRepository;
        }

        @Transactional(readOnly = true)
    public ResponseEntity<?> getKakaoLoginUrl() {
            log.info("login-url-responded");
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
    
    /**
     * 카카오 Oauth Token으로 회원가입
     *
     * 추후 자체 로그인 구현 시
     * 신규 카카오 회원 가입 시 자체 회원가입 여부 확인 필요
     * **/
    @Transactional
    public ResponseEntity<?> JoinWithKakaoOauthToken(String code, HttpServletResponse httpServletResponse) {
        log.info("kakaoAuthToken : {}", code);
            
            // 카카오 토큰 조회
            KakaoTokenResponse kakaoTokenResponse = fetchToken(code);
            
            // 토큰에서 유저 정보 조회
            KakaoUserResponse kakaoUserResponse = fetchUserInfo(kakaoTokenResponse.getAccessToken());

                                    // 유저 정보 추출
                                    Long socialOauthId = kakaoUserResponse.getSocialOauthId();
                                    String email = kakaoUserResponse.getKakaoAccount().getEmail();
                                    String name = kakaoUserResponse.getKakaoAccount().getName();
                                    log.info("socialOauthId : {}, email : {}, name : {}", socialOauthId, email, name);

                                    // 카카오 회원가입 유무에 따른 처리 (socialOauthId, email)
                                    Optional<KakaoOauth> optionalKakaoOauth = kakaoOauthRepository.findBySocialOauthIdAndOauthEmail(socialOauthId, email);
                                    log.info("optionalKakaoOauth selected");
                                    // 기 가입된 유저 토큰 반환 처리
                                    if(optionalKakaoOauth.isPresent()){
                                        log.info("Already Joined Kakao Oauth User");
                                        
                                        // 토큰 발급을 위한 userId, userRole 획득
                                        KakaoOauth kakaoOauth = optionalKakaoOauth.get();
                                        String userId = kakaoOauth.getUser().getId();
                                        UserRole userRole = kakaoOauth.getUser().getRole();
                                        log.info("userId : {}, userRole : {}", userId, userRole);
                                        
                                        // jwt 토큰 발급 및 쿠키 저장
                                        generateJwtTokenAndPutInCookie(httpServletResponse, userId, userRole);

                                        return ResponseEntity.ok(null);
                                    }else {
                                        // Email 기반 로컬 회원가입 여부 조회
                                        // 로컬 회원이 있다면 KakaoOauth에 매핑
                                        // 추후 로직 추가

                                        // 로컬 회원가입 미가입 신규 회원가입 처리
                                        User newUser = User.builder()
                                                .name(name)
                                                .build();
                                        log.info("newUser is built");

                                        // 신규 유저 저장
                                        User user = userRepository.save(newUser);
                                        log.info("user saved");

                                        // KakaoOauth 빌드
                                        KakaoOauth kakaoOauth = KakaoOauth.builder()
                                                .user(user)
                                                .socialOauthId(socialOauthId)
                                                .oauthEmail(email)
                                                .name(name)
                                                .build();
                                        log.info("kakaoOauth is built");

                                        // kakaoOauth 저장
                                        kakaoOauthRepository.save(kakaoOauth);
                                        log.info("kakaoOauth is saved");

                                        // jwt token 발급
                                        String userId = user.getId();
                                        UserRole userRole = user.getRole();
                                        log.info("userId : {}. userRole : {}", userId, userRole);

                                        // jwt 토큰 발급 및 쿠키 저장
                                        generateJwtTokenAndPutInCookie(httpServletResponse, userId, userRole);

                                        return ResponseEntity.ok(null);
                                    }
    }

    /**
     * 1) 인가 코드를 받아 액세스 토큰으로 교환
     */
    public KakaoTokenResponse fetchToken(String code) {
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "카카오_앱키");
        params.add("redirect_uri", "리다이렉트_주소");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        return restTemplate.postForObject(url, request, KakaoTokenResponse.class);
//        return kakaoOauthWebClient.post()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/oauth/token")
//                        .build()
//                )
//                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
//                        .with("client_id", clientId)
//                        .with("client_secret", clientSecret)
//                        .with("redirect_uri", redirectUri)
//                        .with("code", code))
//                .retrieve()
//                .bodyToMono(KakaoTokenResponse.class);
    }


    /**
     * 2) 받은 액세스 토큰으로 사용자 정보 조회
     */
    public KakaoUserResponse fetchUserInfo(String accessToken) { // Mono<KakaoUserResponse>

        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);

        return restTemplate.postForObject(url, request, KakaoUserResponse.class);

//        return WebClient.create("https://kapi.kakao.com")
//                .get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/v2/user/me")
//                        .build()
//                )
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .retrieve()
//                .bodyToMono(KakaoUserResponse.class);
    }
    
    /**
     * jwt token 발급 후 쿠키에 저장하는 메소드
     * **/
    public void generateJwtTokenAndPutInCookie(HttpServletResponse httpServletResponse, String userId, UserRole userRole){
        // jwt 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(userId, userRole);
        String refreshToken = jwtUtil.generateRefreshToken(userId, userRole);
        log.info("accessToken : {}, refreshToken : {}", accessToken, refreshToken);

        // 쿠키에 토큰 추가
        httpServletResponse.addCookie(CookieUtil.generateAccessTokenCookie(accessToken));
        httpServletResponse.addCookie(CookieUtil.generateRefreshTokenCookie(refreshToken));
        log.info("jwt token is put in cookie");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /** 응답 데이터 체크 **/
    public Mono<String> checkResponseData(String code) {
        log.info("kakaoAuthToken : {}", code);

//         return fetchToken(code).map(res -> fetchUserInfo(res.getAccessToken()));
         return null;
    }

}