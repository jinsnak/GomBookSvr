package com.dhflour.gombooksvr.mapper.maria;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MariaTestMapper {
    //int insertBulkProductList(List<Map<String, Object>> paramList);
    Map<String, Object> getCategoryList(Map<String, Object> paramMap);
}