# 🍽 요리조리쿡
레시피를 작성하고 요리 정보를 공유하는 웹 서비스
<br><br>

## 🚀 프로젝트 소개
- **프로젝트명**: 요리조리쿡
- **기간**: 2025.04.29 ~ 진행 중
- **인원**: 2명

### 🎯 주요 기능
- 레시피 작성 및 조회
- 냉장고 파먹기
- 요리팁 및 제철 식재료 정보 공유
- 오늘 뭐먹지, 요리QUIZ 등 요리 관련 사이드 콘텐츠 제공

### ⚙ 개발 환경
#### 💻 Back-end
- **Language**: Java 18
- **Framework**: SpringBoot 3.4.4
- **DB**: MariaDB 11.4.5
- **ORM**: JPA, MyBatis
- **Template Engine**: Thymeleaf

#### 💻 Front-end
- **Language**: JavaScript
- **Library**: JQuery 3.6.3, Bootstrap 5.3.7
- **Markup & Style**: HTML5, CSS3</li>
<br>

## 🗺 서비스 흐름도</h2>
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
<br>

## 🗄 ER 다이어그램
<img alt="Image" src="https://github.com/user-attachments/assets/d6182c58-ecee-4dd3-90d5-6246345ff55c" />
<br><br>

## 💡 트러블슈팅
- 아이디 찾기 후 이메일 인증 없이 비밀번호 재설정 기능 구현하기 [https://hotgamza.tistory.com/88]
- JPA DB 조회 결과 없을 때 처리하기 [https://hotgamza.tistory.com/98]
- 다중 이미지 파일 업로드 기능 구현하기 [https://hotgamza.tistory.com/105]
- 요리팁 등록 후 이미지 바로 안 보이는 문제 해결하기 [https://hotgamza.tistory.com/108]
- 공통 헤더에 로그인 사용자의 프로필 이미지 표시하기 [https://hotgamza.tistory.com/115]
<br>

## 🛠 구현 기능
- 회원가입 및 이메일 인증 기능
- 로그인 및 로그아웃 기능
- 아이디/비밀번호 찾기 및 비밀번호 재설정 기능
- 요리팁 CRUD 기능
- 다중 이미지 파일 업로드 기능
- 요리팁 좋아요 기능
- 프로필 사진 변경 기능
<br><br>

> ▶ 아래의 항목을 클릭하면 상세 내용을 확인할 수 있습니다.  
> ※ 현재 UI는 기능 시연 중심으로 간단히 구성되어 있습니다.
<br>

<details open>
  <summary><h3>📝 회원가입</h3></summary>
  <ul>
    <li>아이디 유효성 검사 (required, 형식, 중복 회원 여부)</li>
    <li>비밀번호 유효성 검사 (required, 형식, 비밀번호 확인과 일치 여부)</li>
    <li>이메일 유효성 검사 (required, 형식, 중복 회원 여부)</li>
    <li>인증코드 이메일 전송 및 검증, 인증 유효시간 설정</li>
    <li>
      <details>
        <summary><b>💻 구현 화면</b></summary>
          <ul>
            <li>
              <b>회원가입 화면</b>
              <p>
                <img width="450" src="https://github.com/user-attachments/assets/2b8f36ce-dd92-4c7d-be35-c0a392d4157c"/>
                <img width="450" src="https://github.com/user-attachments/assets/8793ea17-f6db-491b-ac0d-777fc8a11f46"/>
              </p>
            </li>
            <li>
              <b>이메일 인증코드 화면</b>
              <p>
                <img src="https://github.com/user-attachments/assets/a2191270-e28e-4ce6-a7ea-e1b7973376f6"/>
              </p>
            </li>
          </ul>
      </details>   
    </li>
  </ul>  
</details>
<br>

<details open>
  <summary><h3>🔐 로그인</h3></summary>
  <ul>
    <li>로그인 및 로그아웃</li>
    <li>아이디 저장</li>
    <li>로그인 성공 시, DB 최근 로그인 일시 갱신</li>
    <li>로그인 실패 시, 오류 메시지 출력</li>
    <li>
      <details>
        <summary><b>💻 구현 화면</b></summary>
        <ul>
          <li>
            <b>로그인 화면</b>
            <p>
              <img width="450" src="https://github.com/user-attachments/assets/a6d8fe2d-6700-473e-a9f5-5a4e29b554e3"/>
              <img width="450" src="https://github.com/user-attachments/assets/a0b288b6-ed1d-4828-bdda-6830e008fd1b"/>
            </p>
          </li>
        </ul>
      </details>
    </li>
  </ul>
</details>
<br>

<details open>
  <summary><h3>🔑 아이디/비밀번호 찾기 및 비밀번호 재설정</h3></summary>
  <ul>
    <li>아이디 유효성 검사</li>
    <li>이메일 유효성 검사</li>
    <li>이메일 인증</li>
    <li>비밀번호 재설정</li>
    <li>아이디 찾기 후 바로 비밀번호 찾기 진행 시 이메일 인증 생략</li>
    <li>
      <details open>
        <summary><b>💡 트러블슈팅</b></summary>
        <ul>
          <li>아이디 찾기 후 이메일 인증 없이 비밀번호 재설정 기능 구현하기 [https://hotgamza.tistory.com/88]</li>
        </ul>
      </details>
    </li>
    <li>
      <details>
        <summary><b>💻 구현 화면</b></summary>
        <ul>
          <li>
            <b>아이디 찾기 화면</b>
            <p>
              <img width="450" src="https://github.com/user-attachments/assets/eec0924b-e54c-492d-be50-9d99791b8969"/>
              <img width="450" src="https://github.com/user-attachments/assets/d8ee8f01-8b8f-44d2-9f99-dc5c469423f4"/>
            </p>
          </li>
          <li>
            <b>아이디 찾기 결과 화면</b>
            <p>
              <img width="450" src="https://github.com/user-attachments/assets/380205dc-354d-4686-83d7-ee02073cae8e"/>
            </p>
          </li>
          <li>
            <b>비밀번호 찾기 화면</b>
            <p>
              <img width="450" src="https://github.com/user-attachments/assets/bae34e79-85b0-465c-99bc-0142cebf7e26"/>
              <img width="450" src="https://github.com/user-attachments/assets/04aaa32d-af93-4654-9692-5f8cc16eb29b"/>
            </p>
          </li>
          <li>
            <b>비밀번호 재설정 화면</b>
            <p>
              <img width="450" src="https://github.com/user-attachments/assets/cab8c6f1-9932-442f-acb6-1d75a7a84c37"/>
            </p>
          </li>
        </ul>
      </details>
    </li>
  </ul>
</details>
<br>

<details open>
   <summary><h3>🍳 요리팁</h3></summary>
   <ul>
      <li>요리팁 목록 조회 (정렬, 카테고리별 조회, 페이징 처리)</li>
      <li>요리팁 상세 조회</li>
      <li>요리팁 등록/수정/삭제</li>
      <li>
         <details open>
         <summary><b>💡 트러블슈팅</b></summary>
            <ul>
               <li>JPA DB 조회 결과 없을 때 처리하기 [https://hotgamza.tistory.com/98]</li>
               <li>요리팁 등록 후 이미지 바로 안 보이는 문제 해결하기 [https://hotgamza.tistory.com/108]</li>
            </ul>
         </details>
      </li>
      <li>
         <details>
            <summary><b>💻 구현 화면</b></summary>
            <ul>
               <li>
                  <b>요리팁 목록 화면</b>
                  <p>
                     <img width="1920" height="1488" alt="Image" src="https://github.com/user-attachments/assets/a356e239-5e31-40aa-a53f-975496f09306" />
                  </p>
               </li>
               <li>
                  <b>요리팁 상세 화면</b>
                  <p>
                     <img width="1920" height="1397" alt="Image" src="https://github.com/user-attachments/assets/f4c601f0-abda-4405-9a82-a5083e5d4199" />
                  </p>
               </li>
               <li>
                  <b>요리팁 등록 화면</b>
                  <p>
                     <img width="1920" height="1467" alt="Image" src="https://github.com/user-attachments/assets/91946333-5675-4ea7-929b-bda94f16141a" />
                  </p>
               </li>
               <li>
                  <b>요리팁 수정 화면</b>
                  <p>
                     <img width="1920" height="1467" alt="Image" src="https://github.com/user-attachments/assets/a6cf7428-17ca-4aac-aa76-c16b1f5a5ae3" />
                  </p>
               </li>
            </ul>
         </details>
      </li>
   </ul>
</details>
<br>

<details open>
   <summary><h3>📤 다중 이미지 파일 업로드 및 이미지 미리보기</h3></summary>
   <ul>
      <li>요리팁 등록 시 다중 이미지 파일 업로드</li>
      <li>업로드한 이미지 미리보기 및 삭제</li>
      <li>
         <details open>
            <summary><b>💡 트러블슈팅</b></summary>
            <ul>
               <li>다중 이미지 파일 업로드 기능 구현하기 [https://hotgamza.tistory.com/105]</li>
            </ul>
         </details>
      </li>
      <li>
         <details>
            <summary><b>💻 구현 화면</b></summary>
            <ul>
               <li>
                  <b>다중 이미지 업로드 및 미리보기 화면</b>
                  <p>
                     <img width="849" height="99" alt="Image" src="https://github.com/user-attachments/assets/1b3daa67-a54e-4b2b-bd18-f2ed0da836c5" />
                  </p>
               </li>
            </ul>
         </details>
      </li>   
   </ul>
</details>
<br>

<details open>
   <summary><h3>❤ 좋아요</h3></summary>
   <ul>
      <li>요리팁 좋아요 (목록, 상세)</li>
      <li>Ajax 방식 활용</li>
      <li>좋아요 버튼 클릭시 버튼 UI 변경</li>
      <li>
         <details>
            <summary><b>💻 구현 화면</b></summary>
            <ul>
               <li></li>
            </ul>
         </details>
      </li>
   </ul>
</details>
<br>

<details open>
   <summary><h3>🖼 프로필 사진 변경</h3></summary>
   <ul>
      <li></li>
      <li></li>
      <li></li>
      <li>
         <details>
            <smmary><b>💻 구현 화면</b></smmary>
            <ul>
               <li></li>
               <li></li>
               <li></li>
            </ul>
         </details>
      </li>
   </ul>
</details>
<br>
