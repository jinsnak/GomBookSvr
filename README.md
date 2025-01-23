# GomBookSvr (사내문고 시스템 개발)

## 1) Git 정보
### 1. **[Git 주소]** (https://github.com/jinsnak/GomBookSvr.git)
### 2. **Branch 구성**
main으로 최종 Source를 관리하고 각각의 담당자별 branch를 통해 개발 및 push 후 Pull Request를 통해 main에 반영한다.
main
ㄴ  bjpark (박범진 과장)
ㄴ  sgjang (장성근 사원)
ㄴ  scpark (박성철 부장
### 3. Git 명령어
#### 3-1. Git 원격 저장소 연결
1. Git이 설치 되어 있는지 확인  
'git --version' 
2. Git 초기화  
'git init'
3. 원격 저장소 연결  
'git remote add origin "URL"'
4. 새로운 branch 생성 및 전환  
'git checkout -b "branch 명칭"'
5. 브랜치 상태 확인  
'git branch'
#### 3-2. 수정 후, branch에 적용하는 방식(Local -> My Branch)
1. 코드를 수정한 뒤 변경 사항 스테이징 (변경된 파일 모두)  
'git add . ''
2. 변경 사항을 커밋  
'git commit -m "메시지"'
3. 원격 Branch에 푸쉬  
'git push -u origin bjpark'