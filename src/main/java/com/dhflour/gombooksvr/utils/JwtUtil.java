package com.dhflour.gombooksvr.utils;

import com.dhflour.gombooksvr.beans.UserInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.security.Key;

@Slf4j
@Component
public class JwtUtil {
    // JWT 서명을 위한 Key
    private final Key key;
    // 액세스 토큰 만료 시간 (1일)
    private final long accessTokenExpTime;
    // 액세스 토큰 장기 만료 시간 (1년)
    private final long accessTokenYearExpTime;

    @Autowired
    CommonUtil cu;

    // 생성자: JWT 설정값을 로드하여 초기화
    public JwtUtil(@Value("${jwt.secret.key}") String secretkey,
                   @Value("${jwt.oneday.expiration.time}") long accTime,
                   @Value("${jwt.oneyear.expiration.time}") long accYearTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accTime;
        this.accessTokenYearExpTime = accYearTime;
    }

    // 액세스 토큰 생성 메서드
    public String createAccessToken(UserInfo info) {
        String stayYn = cu.nvlStr(info.getLoginStayYn(), "N");
        String userType = info.getUserType();

        // admin 사용자가 로그인 유지 선택 시 1년 토큰 발급
        if ("ADM".equals(userType) && "Y".equals(stayYn)) {
            log.info("1년케이스");
            return createToken(info, accessTokenYearExpTime);
            // 보통 User 사용자에게는 1일 토큰 발급
        } else if ("EMP".equals(userType)) {
            log.info("DLV1년케이스");
            return createToken(info, accessTokenExpTime);
            // else 구문 처리
        } else {
            log.info("1일케이스: " + userType);
            return createToken(info, accessTokenExpTime);
        }
    }

    // JWT 토큰 생성 메서드
    private String createToken(UserInfo info, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("userId", info.getUserId());
        claims.put("userType", info.getUserType());
        claims.put("userName", info.getUserName());
        claims.put("deptCd", info.getDeptCd());
        claims.put("deptName", info.getDeptName());
        claims.put("validDt", info.getValidDt());
        claims.put("isTmpPassword", info.getIsTmpPassword());
        claims.put("loginStayYn", info.getLoginStayYn());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        JwtBuilder jb = new DefaultJwtBuilder();
        Header jHeader = Jwts.header();
        jHeader.setType("JWT");
        jb.setHeader((Map<String, Object>) jHeader);
        jb.setClaims(claims);
        jb.setIssuedAt(Date.from(now.toInstant()));
        jb.setExpiration(Date.from(tokenValidity.toInstant()));
        jb.signWith(SignatureAlgorithm.HS256, key);

        return jb.compact();
    }

    // 토큰에서 사용자 ID 추출
    public String getUserId(String token) {
        return parseClaims(token).get("userId", String.class);
    }

    // 토큰에서 유효 기간 추출
    public String getValidDt(String token) {
        return parseClaims(token).get("validDt", String.class);
    }

    // 토큰에서 사용자 타입 추출
    public String getUserType(String token) {
        return parseClaims(token).get("userType", String.class);
    }

    // 토큰에서 사용자 이름 추출
    public String getUserName(String token) {
        return parseClaims(token).get("userName", String.class);
    }

    // 토큰에서 부서 코드 추출
    public String getDeptCd(String token) {
        return parseClaims(token).get("deptCd", String.class);
    }

    // 토큰에서 부서명 추출
    public String getDeptName(String token) {
        return parseClaims(token).get("deptName", String.class);
    }

    // 토큰에서 임시 비밀번호 여부 추출
    public String getIsTmpPassword(String token) {
        return parseClaims(token).get("isTmpPassword", String.class);
    }

    // JWT 유효성 검증 메서드
    public boolean validateToken(String token) {
        boolean outBool = true;
        try {
            JwtParser jp = Jwts.parser();
            jp.setSigningKey(key);
            jp.parseClaimsJws(token);
        } catch (ExpiredJwtException eje) {
            outBool = false;
            log.debug("expired token");
        } catch (MalformedJwtException | SecurityException se) {
            outBool = false;
            log.debug("Invalid token");
        } catch (UnsupportedJwtException ue) {
            outBool = false;
            log.debug("Unsupported token");
        } catch (IllegalArgumentException ie) {
            outBool = false;
            log.debug("check token string");
        }
        return outBool;
    }

    // JWT 토큰을 파싱하여 Claims 반환
    public Claims parseClaims(String accessToken) {
        try {
            JwtParser jp = Jwts.parser();
            jp.setSigningKey(key);
            Jws<Claims> jws = jp.parseClaimsJws(accessToken);
            return jws.getBody();
        } catch (ExpiredJwtException eje) {
            return eje.getClaims();
        }
    }

}
