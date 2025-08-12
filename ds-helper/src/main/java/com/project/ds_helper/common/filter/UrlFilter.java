package com.project.ds_helper.common.filter;

import org.springframework.stereotype.Component;

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

}
