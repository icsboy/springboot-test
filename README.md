# 프로젝트 구성
Front-End는 Reactjs를 활용 하였습니다.
Back-End는 Spring Boot를 활용 하였습니다. 
DB는 H2 데이터베이스 엔진을 활용 하였습니다.

# 실행 방법
H2 데이터베이스에 /approval/sql/ddl.sql 파일 내용을 실행하여 테이블 생성 후

Spring Boot 서버를 실행하면 8080포트로 실행되며

Reactjs를 /approval/frontend/approval 경로로 이동하여
### `npm start`
위 명령어를 실행하면 3000번 포트로 실행됩니다

# 문제 해결 전략
- 기능 요구사항을 분석하여 도메인으로 분리 될 수 있는 부분부터 시작하였습니다.
- 그리하여 도출된 Document와 Member의 스키마를 만들어서 테이블을 생성하였습니다.   
- 생성된 도메인을 토대로 CRUD API를 생성하였습니다.
- 기본으로 만든 CRUD API를 Front-End에서 활용하면서 과제에서의 제약사항을 추가 및 변경하는 방식으로 해결 하였습니다.


