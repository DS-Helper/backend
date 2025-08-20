package com.project.ds_helper.domain.user.service;

import com.project.ds_helper.common.util.CookieUtil;
import com.project.ds_helper.common.util.JwtUtil;
import com.project.ds_helper.domain.post.entity.Image;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
//@RequiredArgsConstructor
@Slf4j
public class KakaoOauthService {

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

        public KakaoOauthService(@Qualifier("kakaoOauthWebClient") WebClient kakaoOauthWebClient,
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
        public String getKakaoLoginUrl() {
            log.info("login-url-responded");

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://kauth.kakao.com/oauth/authorize?")
                    .append("client_id=").append(clientId)
                    .append("&redirect_uri=").append(redirectUri)
                    .append("&response_type=code");

        return stringBuilder.toString();

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
    public void JoinWithKakaoOauthToken(String code, HttpServletResponse httpServletResponse) throws InterruptedException {
            log.info("kakaoAuthToken : {}", code);
            
            // 토큰에서 유저 정보 조회 (비동기로 시작 -> 동기화)
            KakaoUserResponse kakaoUserResponse = fetchUserInfo(fetchToken(code).getAccessToken());
                        log.info("kakao user responded successfully");

                        // 유저 정보 추출
                        Long socialOauthId = kakaoUserResponse.getSocialOauthId();
                        String email = kakaoUserResponse.getKakaoAccount().getEmail();
                        String name = kakaoUserResponse.getKakaoAccount().getName();
                        String profileImageUrl = kakaoUserResponse.getKakaoAccount().getProfile().getProfileImageUrl();
                        String gender = kakaoUserResponse.getKakaoAccount().getGender();
                        String ageRange = kakaoUserResponse.getKakaoAccount().getAgeRange();
                        String birthday = kakaoUserResponse.getKakaoAccount().getBirthday();
                        String birthyear = kakaoUserResponse.getKakaoAccount().getBirthyear();
                        log.info("socialOauthId : {}, email : {}, name : {}, profileImageUrl : {}, gender : {}, ageRange : {}, birthday : {},birthyear : {},"
                                , socialOauthId, email, name, profileImageUrl, gender, ageRange, birthday, birthyear);

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

                        }else {
                            // Email 기반 로컬 회원가입 여부 조회
                            // 로컬 회원이 있다면 KakaoOauth에 매핑
                            // 추후 로직 추가

                            // 로컬 회원가입 미가입 신규 회원가입 처리
                            User newUser = User.builder()
                                    .name(name)
                                    .profileImageUrl(profileImageUrl)
                                    .gender(gender)
                                    .ageRange(ageRange)
                                    .birthday(birthday)
                                    .birthyear(birthyear)
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
                        }
    }

    /**
     * 1) 인가 코드를 받아 액세스 토큰으로 교환
     */
    public KakaoTokenResponse fetchToken(String code) { // KakaoTokenResponse
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
                .bodyToMono(KakaoTokenResponse.class)
                .block(Duration.ofMillis(5000));
    }


    /**
     * 2) 받은 액세스 토큰으로 사용자 정보 조회
     */
    public KakaoUserResponse fetchUserInfo(String accessToken) { // Mono<KakaoUserResponse>
        return WebClient.create("https://kapi.kakao.com")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/user/me")
                        .build()
                )
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserResponse.class)
                .block(Duration.ofMillis(5000));
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    


}