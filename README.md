# GomBookSvr (사내문고 시스템 개발)

## 📌 Swagger 정보
1. **[Swagger-UI 주소]** (http://localhost:8081/swagger-ui.html)
2. **[Swagger 문서 주소]** (http://localhost:8081/v3/api-docs)

✅ 단순히 API를 추가/수정한 경우 → Swagger UI 새로고침 (F5) 하면 자동 반영!  
✅ 새로운 Controller를 추가했거나 설정을 변경한 경우 → bootRun 다시 실행 필요!

---

## 📌 Git 정보

### 🌐 1. Git 저장소 주소
- **[Git Repository](https://github.com/jinsnak/GomBookSvr.git)**


### 🌿 2. Branch 구성
**`main`** 브랜치는 최종 소스를 관리하며, 각 담당자별 브랜치를 통해 개발 후 **Pull Request(PR)**를 통해 `main`에 반영함.

📌 **Branch 구조**
```bash
main
├── bjpark (박범진 과장)
├── sgjang (장성근 사원)
└── scpark (박성철 부장)
```

📌 ***Branch 운영 방식***
1. 각 담당자는 자신의 **개인 브랜치**에서 개발을 진행 
2. 변경된 내용을 **commit & push** 후, `main` 브랜치로 ***Pull Request(PR)*** 요청 
3. 코드 리뷰 후 승인되면 `main`에 병합

---

## 📌 Git 명령어 정리

### 🚀 3-1. Git 원격 저장소 연결

1. **Git이 설치되어 있는지 확인**
   ```bash
   git --version
   
2. **Git 초기화**
   ```bash
   git init
   
3. **원격 저장소 연결**
   ```bash
   git remote add origin "URL"

4. **새로운 branch 생성 및 전환**
   ```bash
   git checkout -b "branch 명칭"
   
5. **브랜치 상태 확인**
   ```bash
   git branch

### 🚀 3-2. 수정 후, branch에 적용하는 방식(Local -> My Branch)

1. **코드 수정 후, 변경 사항 스테이징(변경된 모든 파일**
   ```bash
   git add .
2. **변경 사항 Commit**
   ```bash
   git commit -m "메시지"
3. **원격 Branch에 Push**
   ```bash
   git push -u origin bjpark

### 🚀 3-3. 원격 저장소 소스 내려 받기(Main -> My Branch)

1. **Main Branch 소스코드 Pull**
   ```bash
   git pull origin main

---

## 📌 Back-end Source 구성
