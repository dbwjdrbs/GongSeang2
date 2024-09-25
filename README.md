# 공생

### 개발 명세서
| 페이지  | 구성 요소          | 상세 내용                                                                                     |
|---------|-------------------|----------------------------------------------------------------------------------------------|
| P1      | 인트로 페이지     | 외부 데이터 불러오기, DB 접속, 위치 권한 설정                                               |
|         | 위치 권한 설정    | 1. 위치 퍼미션 체크<br>2. 퍼미션 요청 및 처리<br>3. 위치 업데이트 시작                     |
| P2      | 로그인 페이지     | Volley로 로그인 정보 요청, 로그인 성공/실패 처리                                            |
|         | 로그인 버튼       | userID, userPass 입력 후 Login.php로 요청                                                    |
|         | 실패 경우 팝업    | "로그인에 실패하였습니다" 출력                                                                |
| P3      | 회원가입 페이지   | Volley로 회원가입 요청, 성공/실패 처리                                                       |
|         | 회원가입 버튼     | userID, userPass, userName, userAge 입력 후 Register.php로 요청                              |
| P4      | ID/PW 찾기 페이지 | ID 찾기 및 비밀번호 찾기 버튼                                                                  |
| P5      | ID 찾기 페이지    | 이름 및 이메일 입력 후 ID 확인                                                                |
| P6      | PW 찾기 페이지    | 이름, 이메일, 아이디 입력 후 비밀번호 확인                                                    |
| P7      | 메인 페이지       | 시험정보, 커뮤니티, 지도, 마이페이지, 캘린더 버튼                                            |
| P8      | 시험정보 페이지   | 지방직, 국가직 버튼 및 시험 일정 버튼                                                        |
| P9      | 지역 선택 페이지  | 경기도, 강원도, 충청도, 전라도, 경상도 버튼                                                  |
| P10     | 경기도 페이지     | 시험 종류 선택 및 결과 출력                                                                   |
| P11     | 강원도 페이지     | 시험 종류 선택 및 결과 출력                                                                   |
| P12     | 충청도 페이지     | 시험 종류 선택 및 결과 출력                                                                   |
| P13     | 전라도 페이지     | 시험 종류 선택 및 결과 출력                                                                   |
| P14     | 경상도 페이지     | 시험 종류 선택 및 결과 출력                                                                   |
| P15     | 국가직 페이지     | 국가직 경쟁률 및 합격선 데이터 요청                                                           |
| P16     | 시험 일정 페이지  | 각종 시험 일정 버튼 클릭 시 해당 웹사이트로 이동                                            |
| P17     | 커뮤니티 페이지    | 자유게시판, 스터디게시판, 교재 추천 게시판 버튼                                             |
| P18     | 자유게시판        | 게시물 보기 및 글쓰기 페이지 이동                                                              |
| P19     | 스터디 게시판     | 게시물 보기 및 글쓰기 페이지 이동                                                              |
| P20     | 교재 추천 게시판  | 게시물 보기 및 글쓰기 페이지 이동                                                              |
| P21     | 합격 후기 게시판  | 게시물 보기 및 글쓰기 페이지 이동                                                              |
| P22     | 게시물            | 게시물 보기, 댓글창, 댓글쓰기 구현                                                             |
| P23     | 글쓰기 페이지      | 제목 및 내용 입력 후 DB 저장                                                                   |
| P24     | 지도 페이지       | 현재 위치 및 장소 검색 (도서관, 서점, 음식점)                                                |
| P25     | 마이페이지        | 닉네임 변경, 비밀번호 변경, 회원탈퇴, 로그아웃 버튼                                          |
| P26     | 닉네임 변경페이지 | 닉네임 변경 요청 및 유효성 체크                                                                |
| P27     | 비밀번호 변경페이지| 현재 비밀번호 확인 및 새 비밀번호 입력                                                        |
| P28     | 회원탈퇴 페이지    | 비밀번호 확인 후 탈퇴                                                                          |
| P29     | 달력 페이지       | 사용자 달력 일기장, 저장, 수정, 삭제 기능                                                    |

