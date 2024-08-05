/*
 테스트 계정 생성 @password 변수를 이용해서 아래 비밀번호로 하시면 편합니다.
 비밀번호 해시 전 문자열은 qwer1234!@ 입니다.
*/

SET @password = '$2b$12$VUGN6yHZ8aaI2sgL4gKHguWAis/xH9oGF5JwT9.HG6Olt9X0I5NJK';

INSERT INTO USERS(password, email, nickname, nation, profile_img, role, term_agreed, created_at, updated_at)
VALUES(@password, 'admin@test.com', '관리자', 410, null, 'ADMIN', true, NOW(), NOW());

/*
  국가별 테스트 계정
*/
INSERT INTO USERS(password, email, nickname, nation, profile_img, role, term_agreed, created_at, updated_at)
VALUES(@password, 'test@test.com', '테스트', 410, null, 'USER', true, NOW(), NOW()),
      (@password, 'test1@test.com', '테스트1', 840, null, 'USER', true, NOW(), NOW()), -- 미국
      (@password, 'test2@test.com', '테스트2', 124, null, 'USER', true, NOW(), NOW()), -- 캐나다
      (@password, 'test3@test.com', '테스트3', 250, null, 'USER', true, NOW(), NOW()), -- 프랑스
      (@password, 'test4@test.com', '테스트4', 276, null, 'USER', true, NOW(), NOW()), -- 독일
      (@password, 'test5@test.com', '테스트5', 392, null, 'USER', true, NOW(), NOW()), -- 일본
      (@password, 'test6@test.com', '테스트6', 410, null, 'USER', true, NOW(), NOW()), -- 한국
      (@password, 'test7@test.com', '테스트7', 826, null, 'USER', true, NOW(), NOW()), -- 영국
      (@password, 'test8@test.com', '테스트8', 356, null, 'USER', true, NOW(), NOW()), -- 인도
      (@password, 'test9@test.com', '테스트9', 554, null, 'USER', true, NOW(), NOW()), -- 뉴질랜드
      (@password, 'test10@test.com', '테스트10', 484, null, 'USER', true, NOW(), NOW()); -- 멕시코;