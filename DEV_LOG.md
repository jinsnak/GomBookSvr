# Development Log

## 파일명/변경내용/사유 관리 양식
| 파일명        | 변경 내용                    | 사유                        |
|------------|-----------------------------|-----------------------------|
| README.md  | 프로젝트 설명 추가           | 초기 프로젝트 설명 작성 필요|
| DEV_LOG.md | XML 파일 처리 로직 수정      | 데이터 매핑 방식 개선        |
| Config.cs  | 로그 저장 경로 환경 변수화   | 유연한 설정 관리 필요       |

## 변경 이력

### 1. 신규 프로젝트 설정
| 프로젝트 명칭    | 변경 내용                    | 방식                                                |
|------------|-----------------------------|---------------------------------------------------|
| GomBookSvr | 프로젝트 생성 및 초기화      | Spring Boot 환경설정 매뉴얼을 바탕으로 Spring Boot 프로젝트를 생성함. |

### 2. 기능 추가 및 수정
| 파일명                        | 변경 내용                               | 사유                                                    |
|----------------------------|-------------------------------------|-------------------------------------------------------|
| **Project 세팅**             |                                     |                                                       |
| build.gradle               | Dependencies 부분에 mariaDB 라이브러리 추가   | mariaDB 사용을 위해 라이브러리를 추가                              |
| build.gradle               | Dependencies 부분에 mybatis 라이브러리 추가   | 데이터맵핑 라이브러리로써 SQL쿼리를 XML파일이나 애노테이션에 명시적으로 작성하도록 지원    |
| application.properties     | mariaDB 관련 Connection 설정 추가         | mariaDB의 커넥션 정보를 프로퍼티에 저장                             |
| mybatis-config.xml         | mybatis 기본설정 추가                     | resources폴더에 mybatis 관련 config 파일 생성                  |
| **Config**                 | **각 기능별 Config 파일을 관리**             |                                                       |
| config 패키지                 | config 패키지를 gombooksvr 패키지 폴더 아래 생성 | config 패키지를 생성하여 프로젝트에 필요한 config 파일을 관리              |
| MariaDataSourceConfig.java | mariadb의 config 파일을 생성 및 설정         | mariadb와 관련된 config 파일을 생성하여 기초 설정값을 세팅               |
| **beans**                  | **VO 객체 관리**                        |                                                       |
| ResultVO.java              | mariadb의 config 파일을 생성 및 설정         | mariadb와 관련된 config 파일을 생성하여 기초 설정값을 세팅               |
| **utils**                  | **자주 쓰이는 공통 함수 및 모듈 관리**            |                                                       |
| MapToCamelUtil.java        | Camel 형식으로 문자열을 변환하여 return         | USERNAME -> UserName과 같이 변환(alias=cMap)               |
| **Mapper**                 | **DB 호출별 Interface 및 쿼리문을 정의**      |                                                       |
| mapper.maria&oracle 패키지    | mapper.java 파일이 들어갈 패키지를 DB별로 생성    | mapper 패키지를 생성하여 mapper.java 파일들을 DB별로 관리 (Interface) |
| resource/static/mapper 폴더  | mapper.xml 파일이 들어갈 패키지를 DB별로 생성     | mapper 폴더를 생성하여 mapper.xml 파일들을 DB별로 관리 (Query)       |
| **service**                | **DB 및 비즈니스 로직 결과를 Controller에 반환** |                                                       |
| **controller**             | **요청 데이터 파싱 및 service 호출**          |                                                       |

### 2-1. 기능 추가 및 수정

#### 📂 **Project 세팅**
- **build.gradle**
    - 📌 **변경 내용**: Dependencies 부분에 `mariaDB` 라이브러리 추가
    - 📌 **사유**: mariaDB 사용을 위해 라이브러리를 추가

- **application.properties**
    - 📌 **변경 내용**: `mariaDB` 관련 Connection 설정 추가
    - 📌 **사유**: mariaDB의 커넥션 정보를 프로퍼티에 저장

- **mybatis-config.xml**
    - 📌 **변경 내용**: `mybatis` 기본설정 추가
    - 📌 **사유**: `resources` 폴더에 `mybatis` 관련 config 파일 생성

#### 📂 **Config 설정**
- **config 패키지**
    - 📌 **변경 내용**: `config` 패키지를 `gombooksvr` 패키지 폴더 아래 생성
    - 📌 **사유**: 프로젝트에 필요한 설정 파일을 한곳에서 관리하기 위함

- **MariaDataSourceConfig.java**
    - 📌 **변경 내용**: mariadb 관련 설정 추가
    - 📌 **사유**: mariadb와 관련된 기본 설정값을 관리

#### 📂 **VO 객체**
- **ResultVO.java**
    - 📌 **변경 내용**: mariadb의 config 파일을 생성 및 설정
    - 📌 **사유**: mariadb와 관련된 설정을 코드에서 참조하기 위함

#### 📂 **Utils (공통 함수)**
- **MapToCamelUtil.java**
    - 📌 **변경 내용**: Camel 형식으로 문자열 변환 기능 추가
    - 📌 **사유**: `USERNAME -> UserName` 형태로 변환 필요 (`alias=cMap`)

### 2-2. 기능 추가 및 수정

#### 📂 **Project 세팅**
📌 프로젝트 초기 세팅 과정에서 `mariaDB` 및 `mybatis` 관련 설정을 추가하였음.

| 파일명                  | 변경 내용                          | 사유                                         |
|----------------------|-------------------------------|--------------------------------------------|
| build.gradle         | `mariaDB` 라이브러리 추가         | mariaDB 사용을 위한 설정                     |
| application.properties | `mariaDB` Connection 설정 추가 | mariaDB 커넥션 정보를 프로퍼티로 관리         |
| mybatis-config.xml   | `mybatis` 기본설정 추가          | SQL 쿼리를 XML 또는 애노테이션에 작성하도록 지원 |

---

#### 📂 **Config 설정**
📌 각종 설정 파일을 별도의 `config` 패키지에서 관리하도록 변경함.

| 파일명                  | 변경 내용                          | 사유                                         |
|----------------------|-------------------------------|--------------------------------------------|
| config 패키지         | `gombooksvr` 패키지 아래에 `config` 패키지 추가 | 설정 파일을 한 곳에서 관리하기 위함             |
| MariaDataSourceConfig.java | `mariadb` 관련 설정 추가 | mariadb의 기본 설정값을 관리하기 위함 |

---

#### 📂 **공통 기능 (Utils)**
📌 자주 쓰이는 공통 함수 및 모듈을 관리하는 패키지를 구성함.

| 파일명                  | 변경 내용                          | 사유                                         |
|----------------------|-------------------------------|--------------------------------------------|
| MapToCamelUtil.java  | Camel 형식 문자열 변환 추가         | `USERNAME -> UserName` 변환 기능 필요        |



### 3. Back-end Source 구조

### 99. 개념정리(중간중간 추가)
1. **@Primary 어노테이션의 쓰임**

    Spring에서 동일한 타입의 빈(Bean)이 여러 개 존재할 때,  
    기본으로 선택할 빈을 지정하는 어노테이션

2. 