package com.dhflour.gombooksvr.controller;

import com.dhflour.gombooksvr.beans.ResultVO;
import com.dhflour.gombooksvr.service.LoginService;
import com.dhflour.gombooksvr.utils.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "로그인 Controller", description = "로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    CommonUtil cu;;

    @Operation(summary = "로그인처리", description = "사번과 생일(YYYYMMDD)를 이용하여 JWT Token 발급")
    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    public ResponseEntity<ResultVO> doLogin(@RequestBody Map<String, Object> paramMap) throws Exception {
        ResultVO rvo = new ResultVO();

        //필수값 validation
        Map<String, Object> ruleMap = new HashMap<>();
        ruleMap.put("userId", "로그인 아이디를 확인하세요");
        ruleMap.put("password", "비밀번호를 확인하세요");
        Map<String, Object> validateMap = new HashMap<>();
        validateMap = cu.mapValidate(ruleMap, paramMap);

        try{
            if(validateMap.size() > 0) {
                throw new Exception(validateMap.get("errMsg").toString(), new NullPointerException());
            }

            paramMap.put("flag", "EMP");
            Object result = loginService.login(paramMap);
            rvo.setCode(HttpStatus.OK.value());
            rvo.setBody(result);
        } catch (Exception e) {
            rvo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            rvo.setBody(e.getMessage());
        }

        return new ResponseEntity<>(rvo, HttpStatus.OK);
    }
}
