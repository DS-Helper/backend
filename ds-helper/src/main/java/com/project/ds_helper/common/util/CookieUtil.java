package com.project.ds_helper.common.util;

import com.project.ds_helper.common.enums.JwtTokenType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public Cookie generateAccessTokenCookie(String accessToken){
        Cookie cookie = new Cookie(JwtTokenType.ACCESS_TOKEN_NAME.getTokenName(), accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie generateRefreshTokenCookie(String refreshToken){
        Cookie cookie = new Cookie(JwtTokenType.REFRESH_TOKEN_NAME.getTokenName(), refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }

    public String getAuthToken(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(HttpHeaders.AUTHORIZATION)){
                return cookie.getName();
            }
        }
        return null;
    }
}
