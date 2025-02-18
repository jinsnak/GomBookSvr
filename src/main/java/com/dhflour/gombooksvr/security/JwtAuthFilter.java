package com.dhflour.gombooksvr.security;

import com.dhflour.gombooksvr.beans.CustomUserDetails;
import com.dhflour.gombooksvr.beans.ErrorResponseVO;
import com.dhflour.gombooksvr.beans.UserInfo;
import com.dhflour.gombooksvr.beans.UserRoleEnum;
import com.dhflour.gombooksvr.service.LoginService;
import com.dhflour.gombooksvr.utils.CommonUtil;
import com.dhflour.gombooksvr.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.dhflour.gombooksvr.beans.AuthConstants.NO_AUTH_URL_LIST;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final LoginService ls;

    //private final CommonUtil cu;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String reqUri = request.getRequestURI();
        String errMessage = "";
        int errStep = 0;
        boolean isNeedAuth = false;
        boolean isNeedAuthOrigin = false;     // test
        try{
            isNeedAuth = authUriCheckByGpt(reqUri);
            isNeedAuthOrigin = authUriCheck(reqUri);
//            System.out.println("isNeedAuth :" + isNeedAuth + ", reqUri :" + reqUri);
//            System.out.println("isNeedAuthOrigin :" + isNeedAuthOrigin + ", reqUri :" + reqUri);

            // Swagger 페이지에서 실행 시, 인증 없이 통과하기 위한 코드
            String referer = request.getHeader("Referer");      //요청한 직전 페이지 URL을 가져온다.
            String uri = request.getRequestURI();

            // Swagger 에서 호출한 거라면, false 로 변경
            if ((referer != null && referer.contains("/swagger-ui")) || uri.startsWith("/v3/api-docs")) {
                isNeedAuth = false;
            }
//            System.out.println("isNeedAuth :" + isNeedAuth + ", reqUri :" + reqUri);

            errStep = 10;
            errMessage = "토큰정보가 없습니다.";

            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                errStep = 20;
                errMessage = "비정상적인 토큰입니다.";
                String token = authHeader.substring(7);     // "Bearer "를 제외한 실제 토큰정보만 가져온다.
                String tokenTest = authHeader.replaceFirst("^Bearer\\s+", "");
//                System.out.println("tokenTest" + tokenTest);
//                System.out.println("token" + token);


                //0. 토큰의 유효성 검증
                if(jwtUtil.validateToken(token)){
                    //토큰 정보를 가져온다.
                    String userId = jwtUtil.getUserId(token);
                    String validDt = jwtUtil.getValidDt(token);

                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("userId", userId);
                    paramMap.put("token", token);

                    CommonUtil cu = new CommonUtil();
                    //CommonUtil 현재일자 가져오기 (나중에 없애자)
//                    String today = cu.getToday();
//
//                    int intValidDt = Integer.parseInt(validDt);
//                    int intToday = Integer.parseInt(today);

                    //LocalDate를 이용한 날짜 비교
                    LocalDate today = LocalDate.now();
                    LocalDate validDate = LocalDate.parse(validDt, DateTimeFormatter.BASIC_ISO_DATE);

                    //토큰의 validDt가 today보다 큰 경우 (유효)
                    if(!validDate.isBefore(today)) {
//                    if(intValidDt >= intToday){
                        //유저 정보를 가져온다.
                        Map<String, Object> userInfoMap = ls.getLoginUserInfoByToken(paramMap);

                        if (cu.isEmpty(userInfoMap)) {
                            errStep = 21;
                            errMessage = "다시 로그인해주세요";
                            throw  new Exception();
                        }
                        else {
                            if("N".equals(cu.getMapString(userInfoMap, "useYn"))) {
                                errStep = 22;
                                errMessage = "비활성화된 사용자입니다. 관리자에게 문의해주세요";
                                //비활성화 사용자 토큰 삭제
                                ls.deleteUserTokenById(paramMap);

                                throw  new Exception();

                            }
                        }

                        UserInfo userInfo = new UserInfo();
                        userInfo.setUserId(userId);
                        userInfo.setPassword("");
                        userInfo.setUserType(jwtUtil.getUserType(token));

                        CustomUserDetails cud = null;

                        switch (userInfo.getUserType()) {
                            case "ADM":
                                cud = new CustomUserDetails(userInfo, UserRoleEnum.ROLE_ADMIN);
                                break;
                            case "EMP":
                                cud = new CustomUserDetails(userInfo, UserRoleEnum.ROLE_EMP);
                                break;
                            default:
                                errStep = 30;
                                errMessage = "사용자유형 인식 실패";

                                ls.deleteUserTokenById(paramMap);
                                throw  new Exception();
                        }

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(cud, null, cud.getAuthorities());

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                    else {
                        //유효기간 만료
                        errStep = 25;
                        errMessage = "유효일자가 만료된 토큰입니다.";

                        ls.deleteUserTokenById(paramMap);
                        throw  new Exception();
                    }
                }
                else {
                    //토큰 유효성 체크 Error
                    throw new Exception();
                }
            }
            else {
                //토큰이 없는 경우
                throw new Exception();
            }
            filterChain.doFilter(request, response);
        }catch (Exception e){
            if (isNeedAuth == false) {

                UserInfo userInfo = new UserInfo();
                userInfo.setUserId("X");
                userInfo.setValidDt("99991231");

                //인증없음 단계로 부여
                CustomUserDetails cud = new CustomUserDetails(userInfo, UserRoleEnum.ROLE_NOAUTH);

//                System.out.println("Create cud");
                //default token setting & Pass
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(cud, null, cud.getAuthorities());

//                System.out.println("create UsernamePasswordAuthenticationToken");
                //
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                filterChain.doFilter(request, response);
            }
            else {
                setUnAuthError(response, errMessage, errStep);
            }
        }
    }

    private void setUnAuthError(HttpServletResponse response , String message , int errStep) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // step 에 따라서 에러메시지를 다르게 표기한다.
        // step value and meaning - how to execute
        // 10 토큰정보가 없습니다.  - 인증타야하는데 헤더없으면. (메시지를 고치던가 해야함)
        // 20 토큰정보가 비정상입니다. - 정상적이지 않은 토큰임
        // 21 다시 로그인해주세요. - 498
        // 22 고객정보를 확인해주세요. -
        // 25 유효일자 만료입니다.
        // 30 비정상적인 토큰입니다.
        /*
        HttpStatus.FORBIDDEN.value()	            403	인증은 되었지만 권한이 없을 때
        HttpStatus.INTERNAL_SERVER_ERROR.value()	500	서버 내부 오류 발생 시
        HttpStatus.BAD_REQUEST.value()	            400	잘못된 요청 (클라이언트 오류)
        HttpStatus.NOT_FOUND.value()	            404	요청한 리소스를 찾을 수 없을 때
         */

        int statusValue = HttpStatus.UNAUTHORIZED.value();  //code: 401
//        if(errStep >= 10) {
//            statusValue = 498;  //엠싱크 규칙
//        }

        ErrorResponseVO errorResponseVO = new ErrorResponseVO(statusValue, message, LocalDateTime.now().toString());

        String reponseBody = mapper.writeValueAsString(errorResponseVO);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusValue);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(reponseBody);

    }

    // uri가 AUTH를 타야하는 케이스인지만 본다.
    private boolean authUriCheck(String requestUri){
        // 로직변경 - No Auth만 보기로 한다. 이게 타야하는지 안타야하는지만 확인.
        boolean matchBool = true;

        // for문 label
        authListFor:
        for(String matcher : NO_AUTH_URL_LIST){
            String[] matchArr = matcher.split("/");
            String[] reqUriArr = requestUri.split("/");

            // for문 label
            compFor:
            // 두개의 array를 서로 비교하자.
            for(int rCnt = 0 ; rCnt < reqUriArr.length ; rCnt ++) {
                // 로직을 돈다. else이면 안돌고 끝.
                // requestUri보다 matcher가 더 많으면서 , wildcard (*) 가 없으면 match는 false처리를 한다.
                try{
                    if("**".equals(matchArr[rCnt])){
                        // ** 이면 더이상 비교하지않고 나간다 - 이후로는 판단안함.
//                        log.info("** checked");
                        matchBool = true;
                        break compFor;
                    }else if("*".equals(matchArr[rCnt])){
                        // * 이면 일단 이 레벨에서는 OK - pass
//                        log.info("* checked");
                        matchBool = true;
                        continue;
                    }else {
                        // 그 외의 경우에는 path비교를 해서 틀리면 matchBool == false - loop out을 하자.
                        if(matchArr[rCnt].equals(reqUriArr[rCnt]) == false){
                            // 같지않으면 matchBool == false & break
                            matchBool = false;
                            break compFor;
                        }else{
                            // 같다면 진행
                            // if lastStep 이면 true break
                            if((matchArr.length == reqUriArr.length) && (rCnt == (reqUriArr.length -1)) ){
                                matchBool = true;
                                break compFor;
                            }
                            continue;
                        }
                    }
                }catch(ArrayIndexOutOfBoundsException aiobe){
                    matchBool = false;
                    break compFor;
                }
            }

            // for문 다 돌고나서도 matchBool이 true이면 break처리. ==> 더 돌필욘없다.
            if(matchBool == true){
                break authListFor;
            }
        }

        // return 을 뒤집어줌.
        return !matchBool;
    }

    // 이게 더 뛰어나다.. /api/**인 경우 /api까지도 허용함.
    private boolean authUriCheckByGpt(String requestUri) {
        // 기본값: 인증이 필요함 (true)
        boolean requiresAuth = true;

        // 요청 URI를 "/" 기준으로 분할
        String[] reqUriArr = requestUri.split("/");

        // NO_AUTH_URL_LIST를 순회하며 매칭 확인
        for (String matcher : NO_AUTH_URL_LIST) {
            String[] matchArr = matcher.split("/");

            // 길이 비교를 먼저 수행하여 예외 방지
            if (matchArr.length > reqUriArr.length && !matcher.contains("**")) {
                continue;
            }

            boolean isMatched = true; // 해당 matcher가 requestUri와 일치하는지 여부

            for (int i = 0; i < reqUriArr.length; i++) {
                // 길이 초과 방지
                if (i >= matchArr.length) {
                    isMatched = false;
                    break;
                }

                String matchSegment = matchArr[i];
                String requestSegment = reqUriArr[i];

                if ("**".equals(matchSegment)) {
                    // `**`가 나오면 이후 비교 불필요 → 매칭 성공
                    isMatched = true;
                    break;
                } else if ("*".equals(matchSegment)) {
                    // `*`이면 현재 경로만 패스
                    continue;
                } else if (!matchSegment.equals(requestSegment)) {
                    // 경로가 다르면 불일치
                    isMatched = false;
                    break;
                }
            }

            // 하나라도 일치하면 인증 불필요 (즉시 종료)
            if (isMatched) {
                requiresAuth = false;
                break;
            }
        }

        return requiresAuth; // 인증이 필요한지 여부 반환
    }

}
