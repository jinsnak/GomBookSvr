# Development Log

## 파일명/변경내용/사유 관리 양식
| 파일명        | 변경 내용     | 사유                                      |
|------------|-----------|-----------------------------------------|
| README.md  | 프로젝트 설명 | 프로젝트 SPEC, Git 정보 등을 기술                 |
| DEV_LOG.md | 개발 내용 설명  | 초기 프로젝트 설정을 위한 작업 내역 기술                 |

## 변경 이력

### 1. 신규 프로젝트 설정
📌 신규 프로젝트 생성은 **_'Spring Boot 환경설정 매뉴얼_v1.0_20241203.pptx'_** 파일을 기반으로 진행함.

| 프로젝트 명칭    | 변경 내용                    | 방식                                                        |
|------------|-----------------------------|-----------------------------------------------------------|
| GomBookSvr | 프로젝트 생성 및 초기화      | Spring Boot 환경설정 매뉴얼을 바탕으로 Spring Boot 프로젝트를 생성  |

### 2. 기능 추가 및 수정

#### 📂 **Project 세팅**
📌 프로젝트 초기 세팅 과정에서 `mariaDB` 및 `mybatis`, `Oracle` 관련 설정을 추가하였음.

| 파일명                  | 변경 내용                                | 사유                                                      |
|----------------------|--------------------------------------|---------------------------------------------------------|
| build.gradle         | `dependencies`에 implementation       | `mariadb`, `oracle`, `lombok`, `tomcat`, `swagger` 사용   |
| application.properties | `mariaDB`, `oracle` Connection 정보 추가 | DB커넥션 정보(변경되는 경우 변경 필요)                      |
| mybatis-config.xml   | `mybatis` 기본설정 추가                    | SQL 쿼리를 XML 또는 애노테이션에 작성하도록 지원              |

---

#### 📂 **Config 설정**
📌 각종 설정 파일을 별도의 `config` 패키지에서 관리하도록 변경함.

| 파일명                         | 변경 내용                                | 사유                       |
|-----------------------------|--------------------------------------|--------------------------|
| config 패키지                  | `gombooksvr` 패키지 아래에 `config` 패키지 추가 | 설정 파일을 한 곳에서 관리하기 위함     |
| MariaDataSourceConfig.java  | `mariadb` 관련 설정 추가                   | mariadb의 기본 설정값을 관리하기 위함 |
| OracleDataSourceConfig.java | `oracle` 관련 설정 추가                    | oracle의 기본 설정값을 관리하기 위함  |

---

#### 📂 **공통 기능 (Utils)**
📌 자주 쓰이는 공통 함수 및 모듈을 관리하는 패키지를 구성함.

| 파일명                  | 변경 내용                          | 사유                                         |
|----------------------|-------------------------------|--------------------------------------------|
| MapToCamelUtil.java  | Camel 형식 문자열 변환 추가         | `USERNAME -> UserName` 변환 기능 필요        |

---

#### 📂 **VO 객체 정의 (beans)**
📌 자주 쓰이는 공통 함수 및 모듈을 관리하는 패키지를 구성함.

| 파일명           | 변경 내용                         | 사유                                                         |
|---------------|-------------------------------|-----------------------------------------------------------|
| ResultVO.java | Controller에서 반환하는 객체 정의  | Controller에서 반환하는 모든 객체는 `ResultVO` 형태로 반환                 |

---

#### 📂 **Mapper.xml 구성 (resource/static/mapper)**
📌 실제 DB와 연결하여 쓰이는 Query 문을 DB 및 Category 별로 정의 

| 폴더명    | 변경 내용                               | 사유                                                               |
|--------|-------------------------------------|-------------------------------------------------------------|
| maria  | `mariadb`에서 쓰이는 Mapper.xml로 구성      | xml 파일에 id별 Query문을 작성 |
| oracle | `oracle`에서 쓰이는 Mapper.xml로 구성       | xml 파일에 id별 Query문을 작성 |

---

#### 📂 **Mapper.java(Interface) 구성 (package.mapper)**
📌 `Mapper.xml` 파일의 id를 type Name 형태로 정의하여 `Service.java` 에서 불러올 수 있도록 함.

| 폴더명    | 변경 내용                          | 사유                                                               |
|--------|--------------------------------|------------------------------------------------------------------|
| maria  | `mariadb`에서 쓰이는 Mapper.xml로 구성 | xml 파일에 id별 Query문을 작성 |
| oracle | `oracle`에서 쓰이는 Mapper.xml로 구성  | xml 파일에 id별 Query문을 작성 |

---

#### 📂 **Service.java 구성 (package.service)**
📌 요청에 의하여 DB 및 비즈니스 로직을 통해 결과 값을 `Controller.java`에 반환

| 파일명              | 변경 내용                        | 사유                                                       |
|------------------|------------------------------|----------------------------------------------------------------|
| TestService.java | `mariadb`의 Mapper를 이용한 예제 파일 | Select(단순,타겟), Insert, Update, Delete 구성 |
| UserSerivce.java | `oracle`의 Mapper를 이용한 예제 파일  | Select(단순,타겟), Insert, Update, Delete 구성              |

---

#### 📂 **Controller.java 구성 (package.controller)**
📌 요청 데이터 파싱하여 `Service.java` 호출, 결과값을 받아 Front에 반환

| 파일명                 | 변경 내용                     | 사유                                                            |
|---------------------|---------------------------|---------------------------------------------------------------|
| TestController.java | `TestService.java` 호출 예제  |  |
| UserController.java | `UserService.java` 호출 예제 |  |


### 3. Back-end Source 구조

### 99. 개념정리(중간중간 추가)
1. **@Primary 어노테이션의 쓰임**

    Spring에서 동일한 타입의 빈(Bean)이 여러 개 존재할 때,  
    기본으로 선택할 빈을 지정하는 어노테이션


### 개발메모

1. Swagger Config 아직 이관 안함.
    JWT 또는 Spring 관련 Security에 대한 처리를 하지 않아서 필요가 없는건가..
2. POST 형 처리 추가 하기
    POST형 넣어서 뼈대 추가하기