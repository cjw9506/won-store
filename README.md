# won-store(토이 프로젝트)

-   **주제 선정** 

게시판, 수강 신청 사이트, 커뮤니티 게시판 등 많은 후보가 있었지만, 가장 친숙한 쇼핑몰을 만들어보기로 결정했다.

강의를 따라 만들어 경험 해 보기도 했고, 배운 내용을 토대로 기획이나 명세부터 시작해,

혼자 뼈대를 만들어 보고 넣어보고 싶은 기능들을 확장하며 만들어보며, 그때그때 정리하면 많은 도움이 될 것 같다.

명세도 생각나는 대로 보완할 계획이다.

---

### **1\. 개요**

-   프로젝트 이름 : Won-Store(의류 store)
-   개발 인원 : 1명
-   개발 기간 : 2023.03.21 -
-   주요 기능 :
    -   회원 - 회원 가입(User, Admin), 회원 정보 수정, 유효성 검사 및 중복 검사
    -   상품 기능 - 상품 CRUD
    -   주문 기능 - 상품 주문, 주문 내역 조회, 주문 취소
    -   기타 기능 - 상품 재고 관리, 상품 카테고리화
-   개발 언어 : Java17
-   개발 환경 : Gradle - Groovy, SrpingBoot3.04, Java17, Jpa, Thymeleaf
-   데이터베이스 : Mysql
-   형상관리 : Github
-   간단 소개 : 회원가입을 통해 관리자는 상품을 등록 판매하고, 사용자는 물건을 사거나 취소할 수 있다.
-   추가하고 싶은 기능(주요 기능 개발 후) : 생각이 나는대로 추가할 예정!
    -   등록된 상품들 페이징
    -   Google 로그인, Naver 로그인 구현
    -   상품 후기, 상품 Q&A
    -   상품 조회순/최신순 조회 기능
    -   검색 기능
    -   유효성검사 디테일화(닉네임, 비밀번호 형식)
    -   장바구니 기능

---

### **2\. 요구사항 분석**

1.  회원 가입 페이지
    -   유효성 검사
        -   아이디는 최소 4~10자이다.
        -   비밀번호는 최소 6~16자이다.
        -   회원가입 모든 항목은 필수 값이다.
    -   중복검사
        -   이미 존재하는 아이디는 "이미 사용중인 아이디입니다." 메시지 출력
        -   회원가입이 완료되면 메인페이지로 이동한다.
2.  로그인 페이지
    -   회원이 로그인을 하지 않은 경우 이용가능한 페이지
        -   회원가입 페이지
        -   로그인 페이지
        -   상품 목록 페이지
        -   상품 상세 페이지
        -   상품 검색 페이지
        -   그 외 페이지는 로그인페이지로 이동하거나 경고메세지 띄어주기 중 미정
    -   로그인 검사
        -   아이디와 비밀번호가 일치하지 않을 시 "아이디 또는 비밀번호가 일치하지 않습니다" 메세지 출력
        -   로그인 확인 시, 메인 페이지로 이동
3.  회원정보 수정
    -   회원정보 수정은 비밀번호, 주소만 가능
    -   변경 불가능한 칸은 invalid
    -   수정 완료 시 수정 시간 업데이트
4.  상품 등록
    -   상품 등록 시 상품이름, 상품설명, 가격, 수량을 입력해야하고 카테고리를 선택한다.
    -   상품 주문, 취소 시 상품 수량이 변경되어야 한다.
    -   상품 대표이미지와 상세이미지를 등록할 수 있어야 한다.(대표이미지는 필수 입력 값이다)
5.  상품 조회
    -   상품을 등록 순으로 조회 가능해야 한다.
6.  상품 수정
    -   상품이름, 상품설명, 가격, 수량을 변경할 수 있다.
7.  상품 주문
    -   회원은 원하는 상품을 원하는 원하는 수량만큼 주문할 수 있다.
    -   회원은 주문한 내역을 조회할 수 있다.
    -   회원은 주문을 취소할 수 있다.

---

### **3\. DB설계**

대략적인 DB 구조는 이렇다.

처음 Mysql ERD설계를 해봤는데 미숙한 것 같다. 시간 내서 공부하도록 하자.

[##_Image|kage@cuks5q/btr43ZwqFQj/guRrWIRtBvL7kNqRIkTqTk/img.png|CDM|1.3|{"originWidth":1648,"originHeight":1228,"style":"alignCenter","width":623,"height":464,"filename":"스크린샷 2023-03-21 오후 3.49.05.png"}_##]

---

### **5\. API 설계**

미완...
