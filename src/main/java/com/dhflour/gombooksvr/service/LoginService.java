package com.dhflour.gombooksvr.service;

import com.dhflour.gombooksvr.beans.UserInfo;
import com.dhflour.gombooksvr.beans.UserRoleEnum;
import com.dhflour.gombooksvr.mapper.maria.MariaUserMapper;
import com.dhflour.gombooksvr.mapper.oracle.OracleUserMapper;
import com.dhflour.gombooksvr.utils.CommonUtil;
import com.dhflour.gombooksvr.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;

    private final MariaUserMapper mum;
    @Qualifier("oracleSqlSession")
    private final OracleUserMapper oum;

    @Autowired
    private CommonUtil cu;

    //mariaDB를 통한 로그인 때 사용
    @Value("${mariadb.enc.key}")
    private String encKey;

    public String login(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> userMap = new HashMap<String, Object>();

        paramMap.put("encKey", encKey);
        log.debug("CHECKED=="+paramMap.toString());

        userMap = oum.getLoginInfo(paramMap);

        if(userMap == null || userMap.isEmpty()) {
            //입력한 정보가 일치하지 않는 경우,
            throw new Exception("로그인 정보가 일치하지 않습니다. 아이디와 비밀번호를 확인해주세요.");
        }
        else{
            //Parameter로 넘어온 flag 값에 따라, Admin과 User 정보를 구분하여 로그인
            if(!paramMap.get("flag").equals(cu.getMapString(userMap, "userType"))) {
                String outStr = UserRoleEnum.findByCode(cu.getMapString(paramMap, "flag"));
                throw new Exception(outStr + "로그인 정보가 일치하지 않습니다. USER TYPE 오류");
            }
        }

        String outToken = "";

        //임시비밀번호 기능이 필요한 경우 사용
        String isTmpPassword = "N";
        if(userMap.get("isTmpPassword") != null && !"".equals(userMap.get("isTmpPassword").toString().trim())){
            isTmpPassword = userMap.get("isTmpPassword").toString().trim();
        }

        String userType = cu.getMapString(userMap , "userType");
        String validDt = cu.getToday(); // 오늘날자로.

        //로그인 유지 기능이 필요한 경우 사용
        String stayYn = cu.getMapString(paramMap , "loginStayYn");

        //로그인 유지를 체크하고 Admin인 경우, 토큰 만기일자를 1년 추가한다.
        if("Y".equals(stayYn) && "ADM".equals(userType)){
            validDt = cu.addDate(cu.getToday() , "Y" , 1);      //오늘일자, 년, 1
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userMap.get("userId").toString());
        userInfo.setUserType(userMap.get("userType").toString());
        userInfo.setUserName(userMap.get("userName").toString());
        userInfo.setDeptCd(userMap.get("deptCd").toString());
        userInfo.setDeptName(userMap.get("deptName").toString());
        userInfo.setValidDt(validDt);
        userInfo.setIsTmpPassword(isTmpPassword);
        userInfo.setLoginStayYn(cu.getMapString(paramMap , "loginStayYn"));

        outToken = jwtUtil.createAccessToken(userInfo);
        if(!outToken.trim().isEmpty()) {
            userMap.put("token", outToken);
            //토큰값이 존재하면 삭제 후 insert 한다.
            mum.deleteUserTokenById(userMap);
            mum.insertUserToken(userMap);
        }
        log.debug("MAKED TOKEN=="+outToken);
        return outToken;

    }

    //토큰 정보를 이용하여 유저 정보를 가져온다.
    public Map<String, Object> getLoginUserInfoByToken(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap = mum.getLoginUserInfoByToken(paramMap);

        return returnMap;
    }

    //유저ID를 이용하여 토큰 정보를 삭제한다.
    public int deleteUserTokenById(Map<String, Object> paramMap) throws Exception {
        int result = 0;
        result = mum.deleteUserTokenById(paramMap);

        return result;
    }
}
