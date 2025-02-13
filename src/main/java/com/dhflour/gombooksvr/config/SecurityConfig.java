package com.dhflour.gombooksvr.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //CSRF 설정 (Off)
        http.csrf((csrf) -> csrf.disable());

        //CORS 처리
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration corsConf = new CorsConfiguration();
            corsConf.setAllowedOrigins(Arrays.asList("*"));     //모든 도메인의 요청을 허용
//            corsConf.setAllowedOrigins(Arrays.asList("https://oms.chefsfood.com"));     //해당 URL만 허용(이렇게 사용)
            corsConf.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));   //모든 HTTP 메서드 허용
//            corsConf.setAllowedMethods(Arrays.asList("GET", "POST"));   //GET과 POST로만 정해진다면 이렇게도 가능
            corsConf.setAllowCredentials(false);        // 요청 시 쿠키(세션) 및 인증 정보를 포함하지 않음
            corsConf.setAllowedHeaders(Arrays.asList("*"));     //모든 요청 헤더를 허용
            return corsConf;
        }));

        //세션 관리 상태 없음으로 구성, Spring Security가 세션을 생성하거나 사용하지 않음 RESTFul API 특징
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        //모든 요청과 응답의 문자 인코딩을 UTF-8로 강제 설정
        CharacterEncodingFilter cef = new CharacterEncodingFilter();
        cef.setEncoding("UTF-8");
        cef.setForceEncoding(true);
        http.addFilterBefore(cef, UsernamePasswordAuthenticationFilter.class);  //인증필터 적용 이전 UTF-8로 인코딩을 고정

        //JWT토큰을 이용하여 인증하기 때문에 UsernamePasswordAuthenticationFilter 필터보다 이전에 토큰을 이용한 인증을 수행한다.


        //규칙 생성
        http.authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()       //모든 요청에 대해 허용
        );


        //RESTFUL API인 경우 로그인 폼 disable, JWT토큰 사용으로 인한 basic 인증 비활성화 (spring-security 6.1 버전 이후부터는 필요 없음 default disable)
//        http.formLogin().disable();
//        http.httpBasic().disable();

        return http.build();
    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authorize -> authorize
//                .anyRequest().permitAll()
//        );
//        return http.build();
//    }
}
