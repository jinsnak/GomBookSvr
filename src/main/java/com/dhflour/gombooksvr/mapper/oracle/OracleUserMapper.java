package com.dhflour.gombooksvr.mapper.oracle;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OracleUserMapper {
    public List getEmpList();      // Select
    public Map<String, Object> getEmpById(Map<String, Object> paramMap);    // Target Select
    public Map<String, Object> getLoginInfo(Map<String, Object> paramMap);  // Login
    public int updateEmpRmk(Map<String, Object> paramMap);  // Update
    public int insertEmp(Map<String, Object> paramMap);     // Insert
    public int deleteEmpById(Map<String, Object> paramMap);     // Delete

}
