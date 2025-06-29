package com.project.ds_helper.common.filter;

import com.project.ds_helper.common.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    /** 필드 **/
    private final JwtUtil jwtUtil;

    /** 생성자 **/
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    ///  JwtFilter 필터링이 필요가 없는 경로인지 확인
    public boolean isPublicEndpoint(HttpServletRequest request){

        // 요청 경로 확인
        String requestedEndPoint = request.getRequestURI();

        // 요청 경로 검증
        List<String> publicEndpointBox = new ArrayList<>();
        String[] publicEndpoints = {"/users/join", "/users/login"};
        publicEndpointBox.addAll(List.of(publicEndpoints));
        if(publicEndpointBox.contains(requestedEndPoint)){
            return true;
        }
        return false;
    }

    /** 필더 검증 **/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {

        // 공개 경로 여부 확인
        if(isPublicEndpoint(request)){
            log.info("Is Public Endpoint");
            filterChain.doFilter(request, response);
            return ;
        }

        // accessToken 검증
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken == null || !bearerToken.startsWith("Bearer ")){
            log.info("[JwtFilter] bearerToken is Null or bearerToken Not Start With Bearer");
            filterChain.doFilter(request, response);
            return;
        }
        String token = bearerToken.substring(7);
        if(jwtUtil.isExpired(token)){
            log.info("[JwtFilter] JwtToken Is Expired");

            response.setStatus(401);
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
//            writer.print(new body("UNAUTHORIZED", "Access Token Is Expired"));
            return;
        }

        /** 인증 객체 저장 **/
        try{
            String email = jwtUtil.getEmail(token);
            log.info("[JwtFilter] email : {}", email);
            // 추후 권한 추가
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, null, null);

            // IP 등 추가적인 세부 정보를 저장
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 컨텍스트에 인증정보 저장, 이후 필터들에서는 이미 인증된 객체로 인식
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }catch (JwtException jwtException){
            log.info("JwtException Caused");
            throw jwtException;
        }

        /** 다음 필터 호출 **/
        log.info("[JwtFilter] Next Filter");
        filterChain.doFilter(request, response);
    }
}

