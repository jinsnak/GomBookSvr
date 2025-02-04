package com.dhflour.gombooksvr.controller;

import com.dhflour.gombooksvr.beans.ResultVO;
import com.dhflour.gombooksvr.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "유저 컨트롤러", description = "유저 관련 호출 관리")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    UserService us;

    @Operation(summary = "ERP All User", description = "ERP 재직중인 모든 사용자 리스트")
    @RequestMapping(value = "/getEmpList", method = RequestMethod.GET)
    public ResponseEntity<ResultVO> getEmpList() {
        ResultVO resultVO = new ResultVO();

        try{
            resultVO.setCode(HttpStatus.OK.value());
            resultVO.setBody(us.getEmpList());
        }
        catch (Exception e){
            resultVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultVO.setBody(e.getMessage());
        }
        return new ResponseEntity<>(resultVO, HttpStatus.OK);
    }
}
