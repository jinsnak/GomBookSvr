package com.dhflour.gombooksvr.mapper.oracle;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OracleUserMapper {
    public List getEmpList();      //전체 조회
    public Map<String, Object> getEmpById(Map<String, Object> paramMap);    //타겟 조회


}
