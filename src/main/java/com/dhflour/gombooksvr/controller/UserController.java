package com.dhflour.gombooksvr.controller;

import com.dhflour.gombooksvr.beans.ResultVO;
import com.dhflour.gombooksvr.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "OracleDB Controller", description = "Oracle DB 커넥션 및 기능 테스트")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    UserService us;

    @Operation(summary = "ERP 유저 전체 조회", description = "전체 조회 Sample")
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

    @Operation(summary = "ERP 유저 타겟 조회", description = "타겟 조회 Sample")
    @RequestMapping(value = "/getEmpById/{noEmp}", method = RequestMethod.GET)
    public ResponseEntity<ResultVO> getEmpById(@PathVariable("noEmp") String noEmp) {
        ResultVO resultVO = new ResultVO();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("noEmp", noEmp);

        try{
            resultVO.setCode(HttpStatus.OK.value());
            resultVO.setBody(us.getEmpById(paramMap));
        }
        catch (Exception e){
            resultVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return new ResponseEntity<>(resultVO, HttpStatus.OK);
    }

    @Operation(summary = "ERP 유저 비고 Update", description = "Update Sample, param: noEmp, rmk")
    @RequestMapping(value = "/updateEmpRmk", method = RequestMethod.POST)
    public ResponseEntity<ResultVO> updateEmpRmk(@RequestBody Map<String, Object> paramMap) {
        ResultVO resultVO = new ResultVO();

        try {
            Object result = us.updateEmpRmk(paramMap);
            resultVO.setCode(HttpStatus.OK.value());
            resultVO.setBody(result);
        }
        catch (Exception e){
            resultVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultVO.setBody(e.getMessage());
        }

        return new ResponseEntity<>(resultVO, HttpStatus.OK);
    }

    @Operation(summary = "ERP 유저 Insert", description = "Insert Sample, param: noEmp, nmEmp")
    @RequestMapping(value = "/insertEmp", method = RequestMethod.POST)
    public ResponseEntity<ResultVO> insertEmp(@RequestBody Map<String, Object> paramMap) {
        ResultVO resultVO = new ResultVO();

        try {
            Object result = us.insertEmp(paramMap);
            resultVO.setCode(HttpStatus.OK.value());
            resultVO.setBody(result);
        }
        catch (Exception e){
            resultVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultVO.setBody(e.getMessage());
        }

        return new ResponseEntity<>(resultVO, HttpStatus.OK);
    }

    @Operation(summary = "ERP 유저 Delete", description = "Delete Sample, param: noEmp")
    @RequestMapping(value = "/deleteEmp", method = RequestMethod.POST)
    public ResponseEntity<ResultVO> deleteEmp(@RequestBody Map<String, Object> paramMap) {
        ResultVO resultVO = new ResultVO();

        try{
            Object result = us.deleteEmp(paramMap);
            resultVO.setCode(HttpStatus.OK.value());
            resultVO.setBody(result);
        }
        catch (Exception e){
            resultVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultVO.setBody(e.getMessage());
        }

        return new ResponseEntity<>(resultVO, HttpStatus.OK);
    }
}
