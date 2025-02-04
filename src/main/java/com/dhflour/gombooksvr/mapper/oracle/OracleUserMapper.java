package com.dhflour.gombooksvr.mapper.oracle;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OracleUserMapper {
    List getEmpList();

}
