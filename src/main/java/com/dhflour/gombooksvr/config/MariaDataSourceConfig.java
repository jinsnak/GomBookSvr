// 프로젝트를 관리하기위한 package설정 - 서로 다른 패키지로 설정이 되어있으면, 외부 library를 불러쓰더라도 오류의 염려가 없다.
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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

// 해당 클래스가 Spring의 설정 파일임을 명시
@Configuration

// MyBatis의 Mapper 인터페이스가 위치한 패키지를 지정하고, 사용할 SqlSessionFactory를 설정
// value: MyBatis Mapper 인터페이스가 위치한 패키지
// sqlSessionFactoryRef: 해당 Mapper가 사용할 SqlSessionFactory 빈(bean)을 지정
@MapperScan(value="com.dhflour.gombooksvr.mapper.maria", sqlSessionFactoryRef = "mariaFactory")
public class MariaDataSourceConfig {
    // 2개의 데이터베이스 연결이 존재하기 때문에 MariaDB를 기본(Primary) 데이터베이스로 지정
    @Primary
    @Bean(name = "mariaDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.maria") // application.properties에서 "spring.datasource.maria"로 시작하는 속성을 읽어와 DataSource 객체를 생성
    public DataSource mariaDataSource() {
        return DataSourceBuilder.create().build();  // DataSource 객체를 생성 및 반환
    }

    // MariaDB를 사용할 MyBatis SqlSessionFactory를 생성
    @Primary
    @Bean(name = "mariaFactory")
    public SqlSessionFactory mariaSqlSessionFactory(@Qualifier("mariaDatasource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean mariaSqlSessionFactory = new SqlSessionFactoryBean();
        mariaSqlSessionFactory.setDataSource(dataSource);   // 위에서 생성한 mariaDataSource를 설정
        // MyBatis의 전체적인 설정을 담은 설정 파일(mybatis-config.xml)의 위치를 지정
        mariaSqlSessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("mybatis-config.xml"));
        // SQL 쿼리를 포함하는 Mapper XML 파일의 위치를 지정 (MariaDB 관련 Mapper만 로드)
        mariaSqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:static/mapper/maria/**/*.xml"));
        return mariaSqlSessionFactory.getObject();
    }

    // MariaDB 전용 SqlSessionTemplate을 생성하여 반환
    // SqlSessionTemplate은 MyBatis의 SqlSession을 구현한 객체로, Thread-Safe하게 동작하며 SQL 실행을 관리함
    @Primary
    @Bean(name = "mariaSqlSession")
    public SqlSessionTemplate mariaSqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
