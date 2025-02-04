package com.dhflour.gombooksvr.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "com.dhflour.gombooksvr.mapper.oracle", sqlSessionFactoryRef = "oracleFactory")
public class OracleDataSourceConfig {
    @Bean(name = "oracleDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.oracle")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oracleFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("oracleDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean oracleSqlSessionFactory = new SqlSessionFactoryBean();
        oracleSqlSessionFactory.setDataSource(dataSource);
        oracleSqlSessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        oracleSqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:static/mapper/oracle/**/*.xml"));
        return oracleSqlSessionFactory.getObject();
    }

    @Bean(name = "oracleSqlSession")
    public SqlSessionTemplate oracleSqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
