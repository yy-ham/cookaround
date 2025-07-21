# 🍽 요리조리쿡
레시피를 작성하고 요리 정보를 공유하는 웹 서비스

<br><br>

<h2>프로젝트 소개</h2>
<ul>
  <li>프로젝트명: 요리조리쿡</li>
  <li>개발 기간: 2025.04.29 ~ 진행 중</li>
  <li>개발 인원: 2명</li>
  <li>개발 환경</li>
  <ul>
    <li>Back-end
      <ul>
        <li>Java 18</li>
        <li>Framework: SpringBoot 3.4.4</li>
        <li>DB: MariaDB 11.4.5</li>
        <li>ORM: JPA, MyBatis</li>
        <li>템플릿 엔진: Thymeleaf</li>
      </ul>
    </li>
    <li>Front-end
      <ul>
        <li>JavaScript, JQuery</li>
        <li>Framework: Bootstrap</li>
        <li>View: HTML5, CSS3</li>
      </ul>
    </li>
  </ul>
  <li>주요 기능</li>
    <ul>
      <li>레시피 작성 및 조회 </li>
      <li>냉장고 파먹기</li>
      <li>요리팁 및 제철 식재료 정보 공유</li>
      <li>오늘 뭐먹지, 요리QUIZ 등 요리 관련 사이드 콘텐츠 제공</li>
    </ul>
</ul>

<br><br>

<h2>서비스 흐름도</h2>
<pre>
[메인 페이지]
   ├── [제철식재료]
   │         └── [식재료 상세]
   ├── [식재료]
   │         └── [식재료 목록] ──▶ [식재료 상세]
   ├── [냉장고파먹기] ────────────▶ [식재료 상세]
   ├── [레시피]
   │         └── [레시피 목록]
   │                   ├── [레시피 상세] ──▶ [수정/삭제]
   │                   └── [레시피 등록]
   ├── [검색] ──────────────────▶ [레시피 목록]
   ├── [요리팁]
   │         └── [요리팁 목록]
   │                   ├── [요리팁 상세] ──▶ [수정/삭제]
   │                   └── [요리팁 등록]
   ├── [회원가입]
   ├── [로그인]
   │         ├── [아이디 찾기]
   │         ├── [비밀번호 찾기]
   │         └── [마이페이지]
   │                  ├── [내가 쓴 글/후기]
   │                  ├── [회원 정보]
   │                  ├── [좋아요]
   │                  └── [문의 내역]
   │
   ├── [관리자]
   │         ├── [글 관리]
   │         ├── [회원 관리]
   │         └── [문의 내역]
   ├── [오늘 뭐 먹지]
   └── [퀴즈]
</pre>

<br><br>

<h2>ERD</h2>
<img src="https://github.com/user-attachments/assets/67afe794-018f-4105-a076-01c8d3e49c97"/>

<br><br>

<h2>담당 기능</h2>
※ 현재 UI는 기능 시연 중심으로 간단히 구성되어 있습니다.

<br><br>

▶를 누르면 상세 내용을 볼 수 있습니다.
<br><br>

<details open>
  <summary>회원가입</summary>
  <br>
  <ul>
    <li>아이디 유효성 검사 (required, 형식, 중복 회원 여부)</li>
    <li>비밀번호 유효성 검사 (required, 형식, 비밀번호 확인과 일치 여부)</li>
    <li>이메일 유효성 검사 (required, 형식, 중복 회원 여부)</li>
    <li>인증코드 이메일 전송 및 검증, 인증 유효시간 설정</li>
  </ul>
  <br>
  <b>회원가입 화면</b>
  <p>
    <img width="450" src="https://github.com/user-attachments/assets/2b8f36ce-dd92-4c7d-be35-c0a392d4157c"/>
    <img width="450" src="https://github.com/user-attachments/assets/8793ea17-f6db-491b-ac0d-777fc8a11f46"/>
  </p>
  <br>
  <b>이메일 인증코드 화면</b>
  <p>
    <img src="https://github.com/user-attachments/assets/a2191270-e28e-4ce6-a7ea-e1b7973376f6"/>
  </p>
</details>

<details open>
  <summary>로그인</summary>
  <br>
  <ul>
    <li>로그인 및 로그아웃</li>
    <li>아이디 저장</li>
    <li>로그인 성공 시, DB 최근 로그인 일시 갱신</li>
    <li>로그인 실패 시, 오류 메시지 출력</li>    
  </ul>
  <br>
  <p>
    <img width="450" src="https://github.com/user-attachments/assets/a6d8fe2d-6700-473e-a9f5-5a4e29b554e3"/>
    <img width="450" src="https://github.com/user-attachments/assets/a0b288b6-ed1d-4828-bdda-6830e008fd1b"/>
  </p>
</details>

