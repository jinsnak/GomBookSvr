package com.dhflour.gombooksvr.service;

import com.dhflour.gombooksvr.mapper.maria.MariaBookMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    private final MariaBookMapper mariaBookMapper;

    public BookService(MariaBookMapper mariaBookMapper) {
        this.mariaBookMapper = mariaBookMapper;
    }

//    public Map<String, Object> getCategoryList(Map<String, Object> paramMap){
//        Map<String, Object> returnMap = new HashMap<String, Object>();
//
//        returnMap = mariaTestMapper.getCategoryList(paramMap);
//
//        return returnMap;
//    }

    public Object getBookList(String flag){
        Object returnObj;

        List<Map<String, Object>> categoryList = new ArrayList<Map<String, Object>>();
        categoryList = mariaBookMapper.getBookList();

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