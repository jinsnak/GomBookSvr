package com.dhflour.gombooksvr.mapper.maria;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MariaUserMapper {
    int insertUserToken(Map<String, Object> paramMap);
    int deleteUserTokenById(Map<String, Object> paramMap);
    Map<String, Object> getLoginUserInfoByToken(Map<String, Object> paramMap);
    Map<String, Object> getLoginUserInfoByPw(Map<String, Object> paramMap);
}
