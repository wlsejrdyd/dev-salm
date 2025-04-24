
# 🌿 salm 프로젝트

> **살림 정보 기반 Q&A 커뮤니티 + 상품 참조 플랫폼**

**salm**은 살림 노하우를 공유하면서, 자연스럽게 사용한 상품 정보를 함께 전달하는  
지식 공유형 커뮤니티입니다. 질문과 답변 중심의 게시판에서 상품 링크를 첨부하면,  
클릭 한 번으로 외부 쇼핑몰/판매처로 이동할 수 있도록 설계되어 있습니다.

---

## 🔧 실행 방법

### 1. 환경 준비
- Java 17+
- Gradle
- MariaDB 10.5 이상

### 2. DB 설정 (`application.yml`)

```yaml
spring:
  datasource:
    url: jdbc:mariadb://localhost:3306/salm
    username: salm_user
    password: your_password
```

### 3. 실행

```bash
# 프로젝트 디렉토리 이동
cd salm-project

# Gradle wrapper 생성 (필요 시)
gradle wrapper

# 서버 실행
./gradlew bootRun
```

> 기본 포트: `http://localhost:8080`

---

## ✨ 주요 기능

| 구분 | 기능 설명 |
|------|-----------|
| 회원 기능 | 이메일 인증 + OAuth 로그인 |
| 게시판 | 질문 작성 / 답변 등록 / 목록 / 상세 보기 |
| 상품 참조 | 링크 입력 시 자동 미리보기 생성 |
| 관리자 | 회원관리 / 게시판 추가 / 메뉴 이름 수정 |
| UI | Tailwind 기반 반응형 디자인 (PC/모바일) |
| 기능 ON/OFF | 댓글, 북마크, 마이페이지 토글 기능 (admin_config 기반) |

---

## 📁 주요 디렉토리 구조

```
salm-project/
├── src/main/java/kr/salm/
│   └── SalmApplication.java
├── src/main/resources/
│   ├── templates/
│   ├── static/
│   └── application.yml
├── build.gradle
├── README.md
```

---

## 💡 향후 기능 예정

- 마이페이지
- 댓글
- 북마크(스크랩)
- 태그 기반 검색
- 관리자 통계 대시보드
- 다크모드 UI

---

## 🙌 기여자

- **기획 / 백엔드 / UI 개발**: ChatGPT
- **서버 인프라 / DB / 운영**: [@wlsejrdyd](https://github.com/wlsejrdyd)

---

## 🛡 라이선스

MIT License
