/*
 테스트 계정 생성 @password 변수를 이용해서 아래 비밀번호로 하시면 편합니다.
 비밀번호 해시 전 문자열은 qwer1234!@ 입니다.
*/

SET
@password = '$2b$12$VUGN6yHZ8aaI2sgL4gKHguWAis/xH9oGF5JwT9.HG6Olt9X0I5NJK';

INSERT INTO users(password, email, nickname, nation, profile_img, role, term_agreed, created_at, updated_at)
VALUES (@password, 'admin@test.com', '관리자', 410, null, 'ADMIN', true, NOW(), NOW());

/*
  국가별 테스트 계정
*/
INSERT INTO users(password, email, nickname, nation, profile_img, role, term_agreed, created_at, updated_at)
VALUES (@password, 'test@test.com', '테스트', 410, null, 'USER', true, NOW(), NOW()),
       (@password, 'test1@test.com', '테스트1', 840, null, 'USER', true, NOW(), NOW()), -- 미국
       (@password, 'test2@test.com', '테스트2', 124, null, 'USER', true, NOW(), NOW()), -- 캐나다
       (@password, 'test3@test.com', '테스트3', 250, null, 'USER', true, NOW(), NOW()), -- 프랑스
       (@password, 'test4@test.com', '테스트4', 276, null, 'USER', true, NOW(), NOW()), -- 독일
       (@password, 'test5@test.com', '테스트5', 392, null, 'USER', true, NOW(), NOW()), -- 일본
       (@password, 'test6@test.com', '테스트6', 410, null, 'USER', true, NOW(), NOW()), -- 한국
       (@password, 'test7@test.com', '테스트7', 826, null, 'USER', true, NOW(), NOW()), -- 영국
       (@password, 'test8@test.com', '테스트8', 356, null, 'USER', true, NOW(), NOW()), -- 인도
       (@password, 'test9@test.com', '테스트9', 554, null, 'USER', true, NOW(), NOW()), -- 뉴질랜드
       (@password, 'test10@test.com', '테스트10', 484, null, 'USER', true, NOW(), NOW());
-- 멕시코;


--Area 데이터 삽입
INSERT INTO area
    (area_code, name)
VALUES (1, 'SEOUL'),
       (2, 'INCHEON'),
       (3, 'DAEJEON'),
       (4, 'DAEGU'),
       (5, 'GWANGJU'),
       (6, 'BUSAN'),
       (7, 'ULSAN'),
       (8, 'SEJONG'),
       (31, 'GYEONGGIDO'),
       (32, 'GANGWONDO'),
       (33, 'CHUNGCHEONGBUKDO'),
       (34, 'CHUNGCHEONGNAMDO'),
       (35, 'GYEONGSANGBUKDO'),
       (36, 'GYEONGSANGNAMDO'),
       (37, 'JEOLLABUKDO'),
       (38, 'JEOLLANAMDO'),
       (39, 'JEJUDO');

-- Place 데이터 삽입
INSERT INTO place (place_id, area_code, title, address, content_id, content_type_id, overview, lat, lng, likes, modified_time, api_type)
VALUES (1, 1, 'Gyeongbokgung Palace', '161 Sajik-ro, Jongno-gu, Seoul', 12345, 76, 'The main palace of the Joseon Dynasty', 37.579617, 126.977041, 1000, '2023-08-09 10:00:00', 'TOUR'),
       (2, 1, 'Namsan Seoul Tower', '105 Namsan Park-gil, Yongsan-gu, Seoul', 23456, 76, 'Iconic tower of Seoul', 37.551168, 126.988228, 800, '2023-08-09 10:00:00', 'TOUR'),
       (3, 1, 'Myeongdong', 'Myeongdong-gil, Jung-gu, Seoul', 34567, 76, 'Center of shopping and food', 37.563826, 126.983948, 1200, '2023-08-09 10:00:00', 'TOUR'),
       (4, 1, 'Bukchon Hanok Village', '37 Gye-dong-gil, Jongno-gu, Seoul', 45678, 76, 'Traditional hanok village', 37.582978, 126.983685, 900, '2023-08-09 10:00:00', 'TOUR'),
       (5, 39, 'Seongsan Ilchulbong', 'Seongsan-ri, Seogwipo-si, Jeju Special Self-Governing Province', 56789, 76, 'UNESCO World Natural Heritage', 33.458031, 126.942520, 1500, '2023-08-09 10:00:00', 'TOUR'),
       (6, 39, 'Jungmun Tourist Complex', '72beon-gil, Jungmun Tourism Road, Seogwipo-si, Jeju Special Self-Governing Province', 67890, 76, 'Beautiful beach and resort', 33.246877, 126.412535, 1100, '2023-08-09 10:00:00', 'TOUR'),
       (7, 39, 'Hallasan National Park', '2070-61 1100-ro, Jeju-si, Jeju Special Self-Governing Province', 78901, 76, 'Symbol of Jeju, Hallasan', 33.361900, 126.529160, 1300, '2023-08-09 10:00:00', 'TOUR'),
       (8, 39, 'Udo', 'Udo-myeon, Jeju-si, Jeju Special Self-Governing Province', 89012, 76, 'Beautiful island', 33.506111, 126.951389, 1000, '2023-08-09 10:00:00', 'TOUR'),
       (9, 6, 'Haeundae Beach', '264 Haeundaehaebyeon-ro, Haeundae-gu, Busan', 90123, 76, 'Representative beach of Busan', 35.158796, 129.160311, 1400, '2023-08-09 10:00:00', 'TOUR'),
       (10, 6, 'Gamcheon Culture Village', '203 Gamnae 2-ro, Saha-gu, Busan', 12345, 76, 'Machu Picchu of Busan', 35.097859, 129.010021, 1000, '2023-08-09 10:00:00', 'TOUR'),
       (11, 6, 'Jagalchi Market', '52 Jagalchihaean-ro, Jung-gu, Busan', 23456, 82, 'Representative fish market of Busan', 35.096701, 129.030782, 800, '2023-08-09 10:00:00', 'TOUR'),
       (12, 6, 'Taejongdae', '24 Jeonmang-ro, Yeongdo-gu, Busan', 34567, 76, 'Magnificent coastal scenery', 35.053435, 129.087234, 900, '2023-08-09 10:00:00', 'TOUR'),
       (13, 32, 'Sokcho Beach', 'Joyang-dong, Sokcho-si, Gangwon-do', 45678, 76, 'Beautiful beach of the East Sea', 38.191368, 128.601754, 1100, '2023-08-09 10:00:00', 'TOUR'),
       (14, 32, 'Seoraksan National Park', '833 Seoraksan-ro, Sokcho-si, Gangwon-do', 56789, 76, 'Representative mountain national park of Korea', 38.119289, 128.465549, 1300, '2023-08-09 10:00:00', 'TOUR'),
       (15, 32, 'Nami Island', '1 Namisum-gil, Namsan-myeon, Chuncheon-si, Gangwon-do', 67890, 76, 'Beautiful lake island', 37.791350, 127.525319, 1000, '2023-08-09 10:00:00', 'TOUR'),
       (16, 32, 'Gyeongpodae', '94 Jeodong, Gangneung-si, Gangwon-do', 78901, 76, 'Famous moonrise spot of the East Sea', 37.805281, 128.896311, 900, '2023-08-09 10:00:00', 'TOUR');

-- Plan 데이터 삽입
INSERT INTO plans (id, user_id, area_code, status, start_day, end_day, subject, over_view, created_at, updated_at) VALUES
                                                                                                                       (1, 1, 1, 'BEGIN', '2023-09-01', '2023-09-03', 'Seoul Historical Tour', 'A 3-day trip exploring the historical landmarks of Seoul', '2023-08-09 10:00:00', '2023-08-09 10:00:00'),
                                                                                                                       (2, 2, 39, 'BEGIN', '2023-09-15', '2023-09-18', 'Jeju Island Nature Experience', 'A 4-day trip enjoying the beautiful nature of Jeju Island', '2023-08-09 10:00:00', '2023-08-09 10:00:00'),
                                                                                                                       (3, 3, 6, 'BEGIN', '2023-10-01', '2023-10-03', 'Busan Foodie Trip', 'A 3-day trip enjoying the delicious food and landmarks of Busan', '2023-08-09 10:00:00', '2023-08-09 10:00:00'),
                                                                                                                       (4, 4, 32, 'BEGIN', '2023-10-15', '2023-10-17', 'Gangwon-do Healing Trip', 'A 3-day trip relaxing in the beautiful nature of Gangwon-do', '2023-08-09 10:00:00', '2023-08-09 10:00:00');

-- Template 데이터 삽입
INSERT INTO templates (id, plan_id, place_id, days, orders, move_time, place_summary, reasoning)
VALUES
-- 서울 역사 탐방
(1, 1, 1, 1, 1, 0, '조선 왕조의 법궁인 경복궁 관람', '서울 역사 탐방의 시작으로 조선의 중심이었던 경복궁을 방문합니다.'),
(2, 1, 4, 1, 2, 30, '전통 한옥 마을인 북촌한옥마을 산책', '경복궁과 가까운 북촌한옥마을에서 전통 가옥을 구경하며 한국의 전통을 체험합니다.'),
(3, 1, 3, 1, 3, 20, '현대적인 서울의 모습을 볼 수 있는 명동 쇼핑', '역사 탐방 후 현대 서울의 모습을 볼 수 있는 명동에서 쇼핑을 즐깁니다.'),
(4, 1, 2, 2, 1, 30, '서울의 전경을 볼 수 있는 남산서울타워 방문', '서울의 과거와 현재를 한눈에 볼 수 있는 남산서울타워에서 서울의 전경을 감상합니다.'),

-- 제주도 자연 체험
(5, 2, 5, 1, 1, 0, '유네스코 세계자연유산인 성산일출봉 등반', '제주도의 상징적인 자연 명소인 성산일출봉에서 시작하여 제주의 아름다운 경관을 감상합니다.'),
(6, 2, 8, 1, 2, 60, '우도에서 자전거 투어', '성산일출봉 근처의 우도로 이동하여 자전거를 타고 섬을 일주하며 제주의 해안 풍경을 즐깁니다.'),
(7, 2, 6, 2, 1, 90, '중문관광단지에서 해변 휴식', '중문관광단지의 아름다운 해변에서 휴식을 취하고 다양한 해양 활동을 즐깁니다.'),
(8, 2, 7, 3, 1, 60, '한라산국립공원 트레킹', '제주의 중심인 한라산을 등반하며 제주의 자연을 만끽합니다.'),

-- 부산 먹방 여행
(9, 3, 9, 1, 1, 0, '해운대 해수욕장에서 아침 산책', '부산 여행의 시작으로 유명한 해운대 해변에서 상쾌한 아침 산책을 즐깁니다.'),
(10, 3, 11, 1, 2, 40, '자갈치시장에서 신선한 해산물 점심', '부산의 대표적인 수산시장인 자갈치시장에서 신선한 해산물로 점심을 즐깁니다.'),
(11, 3, 10, 2, 1, 30, '감천문화마을 탐방', '알록달록한 감천문화마을을 둘러보며 부산의 문화적 면모를 체험합니다.'),
(12, 3, 12, 2, 2, 40, '태종대에서 해안 절경 감상', '태종대에서 부산의 아름다운 해안 절경을 감상하며 여행을 마무리합니다.'),

-- 강원도 힐링 여행
(13, 4, 13, 1, 1, 0, '속초해변에서 일출 감상', '강원도 여행의 시작으로 속초 해변에서 아름다운 일출을 감상합니다.'),
(14, 4, 14, 1, 2, 30, '설악산국립공원 산책', '설악산의 아름다운 계곡과 폭포를 감상하며 가벼운 산책을 즐깁니다.'),
(15, 4, 15, 2, 1, 120, '남이섬에서 숲 체험', '아름다운 남이섬에서 울창한 숲을 산책하고 다양한 문화 체험을 즐깁니다.'),
(16, 4, 16, 3, 1, 90, '강릉 경포대에서 월출 감상', '강릉 경포대에서 아름다운 동해의 월출을 감상하며 여행을 마무리합니다.');