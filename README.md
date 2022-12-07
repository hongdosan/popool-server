# popool-server 
> 인사내역을 거래하고 관리하는 시스템
> 한이음 프로젝트에서 개발한 MSA 기반 인사내역 거래 시스템을 바탕으로 리팩토링하는 서버입니다.

- 프로젝트 기간 : 2022.11.-continue

## 개요
한이음 MSA Project 진행을 했습니다.
이 프로젝트에서 Eureka, API Gateway, Kafka, Spring OAuth, Spring Batch, Redis, Spring Security, Json Web Token 등 기술을 공부하고 적용했습니다.

하지만, 프로젝트를 진행하면서 깊게 학습하지 않는 자신을 봤습니다.
저는 그저 구현만 하는 개발자가 되어가고 있었습니다.

때문에, 아직 완벽하지 않은 자신을 되돌아보며, 기술 하나씩 다시 깊게 공부하기 위해,
Monolithic Architecture를 먼저 내껄로 만들어야겠다고 생각했습니다.

## 기술 스택
> 해당 프로젝트를 수행하며 사용할 기술 스택
- [x] Java 11, MySQL, Gradle, Spring Boot 2.6.7 
- [x] Spring Security, Json Web Token
- [x] Spirng Swagger, Spring Interceptor
- [x] AWS S3
- [x] Sping Data JPA
- [x] Redis
- [ ] Querydsl
- [ ] Spring Batch, RabbitMQ, Kafka
- [ ] AWS EC2, RDS
- [ ] Docker, Jenkins, nginx

## 서비스 소개
회원들이 자신의 이력을 올리면, 기업에서 이력을 찾아보는 서비스입니다.

## 설계 (바뀔 수 있습니다.)
<img width="794" alt="image" src="https://user-images.githubusercontent.com/31675711/206113602-1c0a4aad-13dc-4391-96f3-0bc5377e3023.png">

## API (바뀔 수 있습니다.)

> 회원 API
<img width="873" alt="image" src="https://user-images.githubusercontent.com/31675711/206115200-6552cd05-f9c6-4871-877c-1c08a92e92cd.png">

> 이력서 API
<img width="877" alt="image" src="https://user-images.githubusercontent.com/31675711/206115548-3e3fb783-daee-4753-b6c3-7f09f78b4e3a.png">
