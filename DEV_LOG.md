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

### 2. Back-end Source 구조

#### 📂 **Project 세팅**
📌 프로젝트 초기 세팅 과정에서 `mariaDB` 및 `mybatis`, `Oracle` 관련 설정을 추가하였음.

| 파일명                  | 변경 내용                                | 사유                                                      |
|----------------------|--------------------------------------|---------------------------------------------------------|
| build.gradle         | `dependencies`에 implementation       | `mariadb`, `oracle`, `lombok`, `tomcat`, `swagger` 사용   |
| application.properties | `mariaDB`, `oracle` Connection 정보 추가 | DB커넥션 정보(변경되는 경우 변경 필요)                      |
| mybatis-config.xml   | `mybatis` 기본설정 추가                    | SQL 쿼리를 XML 또는 애노테이션에 작성하도록 지원              |

---

#### 📂 **VO 객체 정의 (beans)**
📌 자주 쓰이는 공통 함수 및 모듈을 관리하는 패키지를 구성

| 파일명                    | 변경 내용                                | 사유                                         |
|------------------------|--------------------------------------|--------------------------------------------|
| AuthConstants.java     | 보안(인증)과 관련된 상수를 모아둔 클래스              | 인증이 필요한 URL과 불필요한 URL 정의                   |
| CustomUserDetails.java | Spring Security의 `UserDetails` 인터페이스 | 인터페이스를 구현하여 사용자의 인증 및 권한 정보를 제공            |
| ErrorResponseVO.java   | 오류 발생에 대한 Response 객체                | JWT Token 관련 Access 거부시 사용                 |
| ResultVO.java          | Controller에서 반환하는 객체 정의              | Controller에서 반환하는 모든 객체는 `ResultVO` 형태로 반환 |
| UserInfo.java          | 사용자의 기본정보를 저장하는 모델 클래스               | 사용자의 ID, 비밀번호 등을 저장, Getter/Setter 메서드 정의  |
| UserRoleEnum.java      | 사용자의 역할(Role)정보를 관리하는 Enum 클래스       | 사용자 역할 및 코드와 이와 관련된 메서드 정의                 |

---

#### 📂 **Config 설정 (config)**
📌 각종 설정 파일을 별도의 `config` 패키지에서 관리하도록 변경함.

| 파일명                         | 변경 내용                                | 사유                       |
|-----------------------------|--------------------------------------|--------------------------|
| config 패키지                  | `gombooksvr` 패키지 아래에 `config` 패키지 추가 | 설정 파일을 한 곳에서 관리     |
| MariaDataSourceConfig.java  | `mariadb` 관련 설정 추가                   | mariadb의 기본 설정값을 관리 |
| OracleDataSourceConfig.java | `oracle` 관련 설정 추가                    | oracle의 기본 설정값을 관리  |
| SecurityConfig.java         | `JWT`, `SpringSecurity` 관련 설정 추가      | 보안에 대한 기본 설정값을 관리        |

---

#### 📂 **Controller.java 구성 (package.controller)**
📌 요청 데이터 파싱하여 `Service.java` 호출, 결과값을 받아 Front에 반환

| 파일명                 | 변경 내용                     | 사유                                                            |
|---------------------|---------------------------|---------------------------------------------------------------|
| TestController.java | `TestService.java` 호출 예제  |  |
| UserController.java | `UserService.java` 호출 예제 |  |

---

#### 📂 **Mapper.java(Interface) 구성 (package.mapper)**
📌 `Mapper.xml` 파일의 id를 type Name 형태로 정의하여 `Service.java` 에서 불러올 수 있도록 함.

| 폴더명    | 변경 내용                          | 사유                                                               |
|--------|--------------------------------|------------------------------------------------------------------|
| maria  | `mariadb`에서 쓰이는 Mapper.xml로 구성 | xml 파일에 id별 Query문을 작성 |
| oracle | `oracle`에서 쓰이는 Mapper.xml로 구성  | xml 파일에 id별 Query문을 작성 |

---

#### 📂 **보안 규칙 정의 (security)**
📌 보안과 관련된 파일을 정의하는 패키지를 구성

| 파일명               | 변경 내용            | 사유                                                                |
|-------------------|------------------|-------------------------------------------------------------------|
| JwtAuthFilter.java | JWT 토큰 검증 필터를 정의 | JWT 토큰의 존재/유효 여부를 체크한다. |

---

#### 📂 **Service.java 구성 (package.service)**
📌 요청에 의하여 DB 및 비즈니스 로직을 통해 결과 값을 `Controller.java`에 반환

| 파일명               | 변경 내용                        | 사유                                                       |
|-------------------|------------------------------|----------------------------------------------------------------|
| TestService.java  | `mariadb`의 Mapper를 이용한 예제 파일 | Select(단순,타겟), Insert, Update, Delete 구성 |
| UserSerivce.java  | `oracle`의 Mapper를 이용한 예제 파일  | Select(단순,타겟), Insert, Update, Delete 구성              |
| LoginService.java | `oracle`의 Mapper를 이용한 예제 파일  | Select(단순,타겟), Insert, Update, Delete 구성              |

---

#### 📂 **공통 기능 (Utils)**
📌 자주 쓰이는 공통 함수 및 모듈을 관리하는 패키지를 구성함.

| 파일명                 | 변경 내용                    | 사유                              |
|---------------------|--------------------------|---------------------------------|
| MapToCamelUtil.java | Camel 형식 문자열 변환 추가       | `USERNAME -> UserName` 변환 기능 필요 |
| CommonUtil.java     | 프로젝트에 자주 쓰이는 Function 정의 | 공통으로 자주 쓰이는 Function을 정의(A.A관리) |
| JwtUtil.java        | JWT 관련 메서드 정의            | 토큰 생성, 유효성 확인 등 토큰 관련 메서드를 정의   |

---

#### 📂 **Mapper.xml 구성 (resource/static/mapper)**
📌 실제 DB와 연결하여 쓰이는 Query 문을 DB 및 Category 별로 정의 

| 폴더명    | 변경 내용                               | 사유                                                               |
|--------|-------------------------------------|-------------------------------------------------------------|
| maria  | `mariadb`에서 쓰이는 Mapper.xml로 구성      | xml 파일에 id별 Query문을 작성 |
| oracle | `oracle`에서 쓰이는 Mapper.xml로 구성       | xml 파일에 id별 Query문을 작성 |

---

### 3. 프로젝트 생성 후 해야 할 일
1. `application.properties` **파일 DB 접속 정보 확인**

    사용할 데이터베이스의 정보에 따라 수정하여 사용.

2. 

---

### 99. 개념정리(중간중간 추가)
#### 1. **어노테이션의 쓰임**
1. `@Primary`

    Spring에서 동일한 타입의 빈(Bean)이 여러 개 존재할 때,  
    기본으로 선택할 빈을 지정하는 어노테이션

2. `@RequiredArgsConstructor`

    final 필드를 포함한 **생성자를 자동으로 생성**해주는 Lombok 애노테이션  
    **생성자 주입**을 간소화하며, **불변성**을 보장함.  
    `@Autowired`를 통해 주입이 가능하지만 단점이 많아, `@RequiredArgsConstructor`을 사용한 뒤 final 변수로 선언하여 사용한다.

3. `@Component`

    **Spring 컨테이너에 Bean**으로 등록해 관리할 수 있도록 하는 기본 애노테이션
    생성자 주입 및 생명주기 고나리가 필요한 경우 사용함  
    대표적으로 `CommonUtil.class`


#### 2. **CSRF(Cross-Site Request Forgery) (보안)**

브라우저는 쿠키를 자동으로 포함하여 요청을 보낸다.  
예를 들어, 은행사이트에 로그인한 상태에서 악성 웹사이트에 방문하면,  
공격자가 사용자의 계좌에서 돈을 송금하는 요청을 보낼 수 있음.  
이를 방지하기 위해 Spring Security 에서는 CSRF 토큰을 사용하여 요청의 신뢰성을 검증함.  
**다만, REST API 서버(JWT 토큰 기반 인증 사용)에서는 Off한다.(브라우저가 쿠키를 자동 전송하지 않음)**

#### 3. **CORS(Cross-Origin Resource Sharing) (보안)**

기본적으로 브라우저는 다른 도메인의 API 요청을 차단하는 보안 정책을 가지고 있음.
이를 Same-Origin Policy(동일 출처 정책)이라고 함.
하지만 웹 애플리케이션이 다른 도메인의 API와도 통신해야 하는 경우가 있을 수 있음.
이러한 경우 CORS 정책을 설정함.
Ex) `https://mywebsite.com` -> `'https://api.example/com` 요청 시 CORS 설정 필요  
허용하면 `api.example.com` -> `'mywebsite.com` 에서도 사용할 수 있음.

### 개발메모

1. Swagger Config 아직 이관 안함.
    JWT 또는 Spring 관련 Security에 대한 처리를 하지 않아서 필요가 없는건가..
2. POST 방식 Request body 형식
   ```bash
   {
   "noEmp": "101303001",
   "rmk": null
   }

3. JWT 토큰 이관 방식 정리
4. Http Header의 `Bearer `의 의미
    JWT 인증을 사용 할때의 **토큰 타입(Token Type)**을 나타내는 접두사로서 소지자라는 의미로 사용자를 인증함.

    