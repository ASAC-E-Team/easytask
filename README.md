# 이지태스크

<img src=https://user-images.githubusercontent.com/104514223/226188262-f9019745-c370-45cd-b795-8c7f218cf961.png>

<center>"시간이 없거나 도움이 필요할 때, 필요한 시간만큼 일을 맡기세요!"</center>
<center>이지태스크를 모티브로 만든 프리랜서 매칭/아웃소싱 플랫폼 API 서버 토이 프로젝트입니다.</center>

<br>



## 개요

대규모 매칭은 어떻게 이루어질까?

인증과 인가는 어떤 방식으로 동작하는 것일까?

기존 서비스를 고도화 하는 건 어떻게 진행되는 것일까?

이러한 궁금증들을 통해서 직접 이지테스크 서버를 구현해보는 프로젝트를 진행하게 되었습니다.

<br>



## 사용기술 및 환경

- Java 11
- Spring Boot
- MySQL 8.0
- MyBatis
- JPA
- Redis
- JWT

<br>



## 서비스 플로우

<img src=https://user-images.githubusercontent.com/104514223/226188145-1cf98230-fa1f-4edd-a2cb-cf5d29171a89.png>
<img width=100% src=https://user-images.githubusercontent.com/104514223/226188198-20188c5c-cd21-41de-b2ac-54a17de4e114.png>
<img width=100% src=https://user-images.githubusercontent.com/104514223/226188225-2ecaba4b-626b-4f28-8868-f0a4d9af3313.png>

<br>



## 프로젝트 아키텍처 흐름도

<img width=100% src=https://user-images.githubusercontent.com/104514223/226256792-ea8e0f99-5cf7-4a35-a0d1-19d4b4c4fd8a.png>

<br>



## ER Diagram

<img width=100% src=https://user-images.githubusercontent.com/104514223/226187940-3369a77f-4740-4980-895a-2e5943ca5e70.png>

<br>



## 기능 구현

https://github.com/ASAC-E-Team/easytask/wiki/%EA%B8%B0%EB%8A%A5-%EC%A0%95%EC%9D%98

<br>



## 이슈 및 해결 과정

<br>

### 1. 로그아웃시 클라이언트에 있는 토큰의 처리 - by. 곽진규

이번 프로젝트는 JWT를 이용하여 로그인을 진행했다.

JWT 기반으로 로그인을 구현 했을 시 로그아웃을 구현하는 방법들로는

1. 클라이언트에 있는 토큰을 삭제한다.
2. 서버에 로그아웃 한 토큰의 리스트에 해당 토큰의 정보를 추가해서 해당 토큰으로 인증 요청시 거절한다.

클라이언트에 있는 토큰을 삭제하는 게 간단해 보여서 이를 진행하려 했는데 서버에서는 클라이언트의 JWT를 직접 삭제하는 건 불가능하다는 걸 깨닫고 2번으로 진행하게 되었다.

처음에는 DB에 테이블을 하나 만들어 그곳에다가 로그아웃 한 토큰의 정보를 저장하였는데 토큰의 유효시간은 어차피 30분인데 이 값을 계속 보관하는 건 자원낭비라고 생각하게 되었다. 그렇다고 DB의 값을 30분마다 계속 삭제하는 건 서버에 부담을 줄 것이라 생각하였고 Redis를 이용한 메모리 방법을 선택하게 되었다.

<br>

### 2. 대규모가 되었을 때 매칭을 위한 DB 조회 최적화 - by. J Lee

매칭 시스템을 구현하다가 우리가 기획했던 매칭이 DB의 여러 테이블을 조회하고 풀 스캔을 하는 특징이 있음을 발견했다.
이 특징으로 인해 매칭은 한 번이 아닌 몇 번의 트랜잭션과 DB 풀 스캔으로 인한 긴 locking 시간을 갖게 되는 문제가 있었다.

이에 대한 고민과 해결과정을 포스팅으로 남겼다.

- [https://jinlee.netlify.app/posts/easytask/matching_db_search_optimization/](https://jinlee.netlify.app/posts/easytask/matching_db_search_optimization/)

<br>

### 3. 메일링 기능의 비동기화 - by. J Lee

매칭 시스템이 돌아가면서 알림으로 SMTP 메일링 기능이 수행되도록 구현했다.

처음에는 단순히 서비스 레이어에서 메일링 서비스 로직을 호출했다.  
고객이 매칭을 신청하면 고객에게 매칭 신청이 시작되었음을 알리는 메일 전송이 완료가 되고나서야 Client에 신청이 완료되었다는 응답을 내려주었다.  
동기적으로 해당 과정이 단일 Thread에서 메일링까지 동작한 것이다.  
이는 우리의 WAS가 다른 요청에 응답하는데 지장이 생기게 하였다.

이를 해결하기 위해 메일링 서비스를 비동기적으로 처리 하고자 했다.
스프링에서 제공하는 AsyncConfigurer의 구현체로 비동기 설정을 만들고 `@EnableAsync` 옵션을 주었다.

기존의 메일링 서비스 로직을 해당 설정에 등록하여 비동기적으로 처리할 수 있게 수정하였다.
