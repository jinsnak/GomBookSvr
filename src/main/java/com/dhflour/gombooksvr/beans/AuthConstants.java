package com.dhflour.gombooksvr.beans;

public class AuthConstants {
    private AuthConstants() {}

    // 인증이 필요 없는 목록
    public static final String[] NO_AUTH_URL_LIST = {"/api/common/**", "/api/login/**"};

    //인증
    public static final String[] AUTH_URL_LIST = {"/api/book/**"};
}
