package com.project.ds_helper.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class UrlFilter {

    private static final String[] SECURITY_FILTER_CHAIN_PASS_URL = {
            "/api/v1/auth",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger"
    };

    private static final String[] JWT_FILTER_CHAIN_PASS_URL = {
            "/api/v1/auth",
            "/v3/api-docs",
            "/swagger",
            "/swagger-ui"
    };

    /**
     * JWT FILTER PASS 경로 확인
     * **/
    public static boolean isPublicPath(String requestUri){
        for(String url : JWT_FILTER_CHAIN_PASS_URL){
            if(url.equals(requestUri)){
                return true;
            }
        }
        return false;
    }


    private static final String LOG_OUT_PATH = "/auth/logout";

    public static String[] getSecurityFilterPassPath(){
        return SECURITY_FILTER_CHAIN_PASS_URL;
    }

    public static boolean checkIfPublicPath(String requestUri){
        log.info("requestUri : {}", requestUri);
        for(String path : JWT_FILTER_CHAIN_PASS_URL){
            if(requestUri.startsWith(path)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfLogoutPath(String requestUri){
        return LOG_OUT_PATH.equals(requestUri);
    }

}
