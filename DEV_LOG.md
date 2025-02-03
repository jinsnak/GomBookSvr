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
| 파일명                        | 변경 내용                               | 사유                                                 |
|----------------------------|-------------------------------------|----------------------------------------------------|
| build.gradle               | Dependencies 부분에 mariaDB 라이브러리 추가   | mariaDB 사용을 위해 라이브러리를 추가                           |
| build.gradle               | Dependencies 부분에 mybatis 라이브러리 추가   | 데이터맵핑 라이브러리로써 SQL쿼리를 XML파일이나 애노테이션에 명시적으로 작성하도록 지원 |
| application.properties     | mariaDB 관련 Connection 설정 추가         | mariaDB의 커넥션 정보를 프로퍼티에 저장                          |
| mybatis-config.xml         | mybatis 기본설정 추가                     | resources폴더에 mybatis 관련 config 파일 생성               |
| config 패키지                 | config 패키지를 gombooksvr 패키지 폴더 아래 생성 | config 패키지를 생성하여 프로젝트에 필요한 config 파일을 관리           |
| MariaDataSourceConfig.java | mariadb의 config 파일을 생성 및 설정         | mariadb와 관련된 config 파일을 생성하여 기초 설정값을 세팅            |
| mapper.maria&oracle 패키지    | mapper.java 파일이 들어갈 패키지를 DB별로 생성    | mapper 패키지를 생성하여 mapper.java 파일들을 DB별로 관리          |
| resource/static/mapper 폴더  | mapper.xml 파일이 들어갈 패키지를 DB별로 생성     | mapper 폴더를 생성하여 mapper.xml 파일들을 DB별로 관리            |

### 3. Back-end Source 구조

### 99. 개념정리(중간중간 추가)
1. **@Primary 어노테이션의 쓰임**

    Spring에서 동일한 타입의 빈(Bean)이 여러 개 존재할 때,  
    기본으로 선택할 빈을 지정하는 어노테이션

2. 