<div align=center>
  <img src="https://github.com/kchaeys2/umc_I/assets/106591445/e5f4f195-aefc-4053-aee0-aa4555fa831b"/>
</div>

## [아이] 아름답게 이별하는 법
> #아이 #아름다운_이별 #노령견 #펫로스 #커뮤니티_기반_거래_어플
> ![Untitled](https://github.com/kchaeys2/umc_I/assets/106591445/3cae2283-925d-4b57-b3cb-287e3ed94ecc)

### “행복하게 함께하고, 아름답게 이별한다.”

노령의 반려동물을 키우는 반려인이나 펫로스로 고통받는 사람들을 위한 커뮤니티이자,**중고거래 어플  <아이>** 입니다.

#### 📌 서비스 목표
- 반려동물을 떠나보내고 남은 용품들을 다른 반려인들에게 나누며 이별의 아픔을 극복한다.
- 아픈 반려동물을 키우며 생긴 꿀팁들을 공유한다.
- 지치고 외로운 반려인들이 서로를 위로한다.

<br/><br/>

## 📌 기능 설명
![Untitled2](https://github.com/kchaeys2/umc_I/assets/106591445/5b96df72-4fdd-4a86-b807-8063c33b7ca8)


### ⭐ 회원가입/로그인

<br/>

### ⭐ [아이홈] 커뮤니티 (효빈)
- 인기글
    - 1시간마다 갱신
    - 조회수 순으로 20개 조회 가능
- 전체글 : 아이홈(커뮤니티)에 업로드 되는 글을 최신순으로 볼 수 있음
- 카테고리
  - 이야기방
    - 수다방,질문방,정보방
  - 일기장
    - 간호 일기, 무지개 일기
  - 장터 후기
- 댓글과 하트

### ⭐ [나눔장터] 중고 거래 (현재)
- 인기글
  - 1시간마다 갱신
  - 조회수 순으로 18개
- 전체글 : 나눔장터에 업로드 되는 모든 물품을 최신순으로 볼 수 있음
- 카테고리
  - 맘마/까까, 장난감, 영양제/약, 간호용품, 기타
- 찜(좋아요 기능)
  
### ⭐ 채팅 (채연) -  webSocket활용
- 채팅 목록
- 메세지 전송
- 채팅방 나가기
  
https://github.com/kchaeys2/umc_I/tree/main/src/main/java/com/umc/i/src/chat

### ⭐ 마이페이지 (채연)
- 회원정보 수정
- 작성한 글 목록 보기
  - 작성한 글 : 커뮤니티에 올린 게시글
  - 일기장 : 일기장에 올린 게시글
  - 나눔장터 : 장터에 올린 게시글
- 활동 내역
   - 좋아요한 게시글 (커뮤니티에서)
   - 찜한 물품들 (나눔장터)
   - 신고 내역
   - 차단한 사용자
  - 기타
    - 문의 하기
    - 탈퇴

https://github.com/kchaeys2/umc_I/tree/main/src/main/java/com/umc/i/src/mypage

<br/><br/>

## 👥 팀 소개

> 개발 기간 : 2023.01 ~ 2023.02


<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/Hyobeen-Park"><img src="https://avatars.githubusercontent.com/u/98209004?v=4" width="150px;" alt=""/><br /><b>박효빈</b></a><br /></td>
      <td align="center"><a href="https://github.com/HyeonJaePark"><img src="https://avatars.githubusercontent.com/u/64001045?v=4" width="150px;" alt=""/><br /><b>박현재</b></a><br /></td>
      <td align="center"><a href="https://github.com/kchaeys2"><img src="https://avatars.githubusercontent.com/u/106591445?v=4" width="150px;" alt=""/><br /><b>김채연</b></a><br /></td>
     <tr/>
      <td align="center">Backend</td>
      <td align="center">Backend</td>
      <td align="center">Backend</td>
    </tr>
  </tbody>
</table> 

<br/><br/>

## Stacks 🐈
**Framework**
- Java 11
- Spring Boot 2.7.7

**Dependencies**
- Spring JDBC
- thymeleaf (채팅 test)
- websocket

**Infra**
- AWS EC2
- AWS S3
- AWS RDS
- Nginx

**Database**
- MySQL (Local, Deploy DB)

<br/><br/>

## ERD
![아이 db drawio](https://github.com/kchaeys2/umc_I/assets/106591445/a6d3efb6-0f75-4bef-90c3-a5e7f02429b2)
