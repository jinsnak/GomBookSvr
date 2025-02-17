package com.dhflour.gombooksvr.mapper.maria;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MariaBookMapper {
    //int insertBulkProductList(List<Map<String, Object>> paramList);
    List getBookList();
}