# GomBookSvr (사내문고 시스템 개발)

## Swagger 정보
1. **[Swagger-UI 주소]** (http://localhost:8081/swagger-ui.html)
2. **[Swagger 문서 주소]** (http://localhost:8081/v3/api-docs)

✅ 단순히 API를 추가/수정한 경우 → Swagger UI 새로고침 (F5) 하면 자동 반영!  
✅ 새로운 Controller를 추가했거나 설정을 변경한 경우 → bootRun 다시 실행 필요!

---

## Git 정보
1. **[Git 주소]** (https://github.com/jinsnak/GomBookSvr.git)
2. **Branch 구성**
main으로 최종 Source를 관리하고 각각의 담당자별 branch를 통해 개발 및 push 후 Pull Request를 통해 main에 반영한다.  
main  
ㄴ  bjpark (박범진 과장)  
ㄴ  sgjang (장성근 사원)  
ㄴ  scpark (박성철 부장)  

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

### 🚀 3-3. 원격 저장소 소스 내려 받기(Master -> My Branch)

1. **Main Branch 소스코드 Pull**
   ```bash
   git pull origin master

---

## 2) Back-end Source 구성
