package com.dhflour.gombooksvr.beans;

public class AuthConstants {
    private AuthConstants() {}

    // 인증이 필요 없는 목록
    public static final String[] NO_AUTH_URL_LIST = {
            "/api/common/**",
            "/api/login/**",
            "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/v3/api-docs", "/webjars/**"
    };

    //인증이 필요한 목록(사실상 의미가 없다. JwtAuthFilter에서 없는 것만 체크해서 분개하기 때문)
    public static final String[] AUTH_URL_LIST = {"/api/book/**"};
}
