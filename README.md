
# salm 프로젝트

> 살림 정보 공유 + 자연스러운 상품 연결 기반의 지식 커뮤니티 플랫폼

---

## 프로젝트 소개

**salm**은 일상 속 살림 노하우를 질문/답변 형태로 공유하고,  
자연스럽게 상품을 참조/연결할 수 있도록 설계된 커뮤니티 기반 플랫폼입니다.  
누구나 쉽게 정보를 공유하고, 필요한 물품은 클릭 한 번으로 확인할 수 있습니다.

---

## 주요 기능

- 회원가입 / 로그인 (이메일 인증 + Google OAuth)
- 질문/답변 게시판 (CRUD)
- 게시글 내 상품 링크 자동 미리보기 + 외부 이동
- 반응형 UI (TailwindCSS 기반)
- 관리자 페이지 (회원/게시판/메뉴 관리)
- 기능 ON/OFF 관리 시스템 (DB 기반)

---

## 기술 스택

| 항목 | 기술 |
|------|------|
| 백엔드 | Java 17, Spring Boot 3.x, Gradle |
| 프론트엔드 | Thymeleaf, TailwindCSS, Alpine.js |
| 인증 | Spring Security, OAuth2, SMTP |
| DB | MariaDB 10.5.22 |
| 배포환경 | Rocky Linux 9.x, Nginx, HTTPS, .jar 실행형 |

---

## DB 주요 테이블

- `user`: 사용자 정보
- `question`, `answer`: 게시판 구조
- `product`, `question_product`: 상품 링크 참조
- `category`: 게시글 분류
- `admin_config`: 관리자 기능 설정
- `email_token`: 이메일 인증 토큰

---

## 향후 확장 기능

- 마이페이지
- 댓글 시스템
- 북마크(스크랩)
- 태그 검색
- 관리자 통계 대시보드
- 다크모드
- 커머스 전환율 통계

---

## 라이선스

본 프로젝트는 MIT 라이선스를 따릅니다.
