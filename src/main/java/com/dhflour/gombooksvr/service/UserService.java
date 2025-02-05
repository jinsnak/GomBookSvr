package com.dhflour.gombooksvr.service;

import com.dhflour.gombooksvr.mapper.oracle.OracleUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Qualifier("oracleSqlSession")
    private final OracleUserMapper oum;

    @Autowired
    public UserService(OracleUserMapper oum) {
        this.oum = oum;
    }

    //전체 조회
    public List<Map<String, Object>> getEmpList(){
        List<Map<String, Object>> empList = oum.getEmpList();
        Object oneObj = empList.get(0);
        System.out.println("FirstMap==" + oneObj.toString());
        return empList;
    }

    //타겟 조회
    public Map<String, Object> getEmpById(Map<String, Object> paramMap) throws Exception{
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap = oum.getEmpById(paramMap);

        return returnMap;
    }

}
