package com.project.ds_helper.common.util;

import com.project.ds_helper.common.enums.JwtTokenType;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public static Cookie generateAccessTokenCookie(String accessToken){
        Cookie cookie = new Cookie(JwtTokenType.ACCESS_TOKEN_NAME.getTokenName(), accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }

    public static Cookie generateRefreshTokenCookie(String refreshToken){
        Cookie cookie = new Cookie(JwtTokenType.REFRESH_TOKEN_NAME.getTokenName(), refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }
}
