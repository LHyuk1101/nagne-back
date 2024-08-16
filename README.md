# Nagne

## 프로젝트 개요
**Nagne**는 외국인을 대상으로 한국 여행을 돕기 위해 한국의 관광지, 맛집, 숙소 등에 대한 정보를 제공하고, AI를 통해 여행 플랜을 컨설팅해주는 웹사이트입니다. 이 사이트는 영문 지원을 통해 외국인들이 쉽게 한국 여행을 계획할 수 있도록 돕습니다.

## 와이어 프레임
[Nagne 와이어프레임](https://www.figma.com/design/EERAquCIinaBSf86l7vISj/Nagne?node-id=0-1&t=19c5ewdttuUESI4S-1)

## ERD
[Nagne ERD](https://www.erdcloud.com/d/pFGWdEK4ngZuDnk4k)

## 기술 스택

### Backend
- **Spring Framework**: 백엔드 애플리케이션의 핵심 프레임워크로 사용
- **Spring Boot**: 마이크로서비스 아키텍처와 간편한 설정을 지원
- **Spring Security**: OAuth(구글, 메타) 소셜 로그인 기능 구현
- **Spring Data JPA**: 데이터베이스와의 상호작용을 위한 ORM 도구

### Batch Processing
- **Spring Batch**: 관광지, 숙소, 맛집 정보를 배치 작업을 통해 대량으로 처리 및 관리
  - **관광지**: tourApi
  - **숙소**: Booking API
  - **맛집**: Google 장소 API

### AI
- **LangChain**: 오픈소스 Flowise를 통해 LangChain 노드 구현
- **LangSmith**: 디버깅을 통한 모델 최적화 (사용 모델: gpt-4.o-mini)

### Database
- **MySQL**: 관계형 데이터베이스로 사용

### CI/CD
- **자동 배포**: GCP(Spring, RDB), 개인 서버(JENKINS, Flowise) 환경으로 구축

### 이미지 저장소
- **AWS S3**: 이미지 URL을 읽어 임시파일을 생성하고 S3 버킷에 물리적으로 저장 및 관리

### Frontend
- **React + Vite**: 프론트엔드 개발을 위한 JavaScript 프레임워크 및 빌드 도구
- **MUI**: React UI 프레임워크
- **styled-components**: CSS-in-JS 스타일링
- **react-query**: 서버 상태를 관리하고, 데이터 페칭 로직을 효율적으로 관리
- **react-datepicker**: 날짜 선택을 위한 컴포넌트
- **swiper**: 슬라이더/캐러셀 구현을 위한 라이브러리

### Docker
- **Docker**: 개발 및 배포 환경 유지를 위해 Docker를 사용

[![My Skills](https://skillicons.dev/icons?i=java,figma,gitlab,jenkins,docker,spring,vite,mysql,discord&theme=light)](https://skillicons.dev) 
[![My Skills](https://skillicons.dev/icons?i=aws,gcp,react&perline=3)](https://skillicons.dev)
