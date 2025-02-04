package com.dhflour.gombooksvr.service;

import com.dhflour.gombooksvr.mapper.maria.MariaTestMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestService {
    private final MariaTestMapper mariaTestMapper;

    public TestService(MariaTestMapper mariaTestMapper) {
        this.mariaTestMapper = mariaTestMapper;
    }

//    public Map<String, Object> getCategoryList(Map<String, Object> paramMap){
//        Map<String, Object> returnMap = new HashMap<String, Object>();
//
//        returnMap = mariaTestMapper.getCategoryList(paramMap);
//
//        return returnMap;
//    }

    public Object getCategoryList(String flag){
        Object returnObj;

        List<Map<String, Object>> categoryList = new ArrayList<Map<String, Object>>();
        categoryList = mariaTestMapper.getCategoryList();

        if("CNT".equals(flag)){
            if(!categoryList.isEmpty()){
                returnObj = categoryList.size();
            }else{
                returnObj = 0;
            }
        }else{
            returnObj = categoryList;
        }

        return returnObj;
    }

}