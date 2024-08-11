/*
 테스트 계정 생성 @password 변수를 이용해서 아래 비밀번호로 하시면 편합니다.
 비밀번호 해시 전 문자열은 qwer1234!@ 입니다.
*/

SET @password = '$2b$12$VUGN6yHZ8aaI2sgL4gKHguWAis/xH9oGF5JwT9.HG6Olt9X0I5NJK';

INSERT INTO users(password, email, nickname, nation, profile_img, role, term_agreed, created_at, updated_at)
VALUES(@password, 'admin@test.com', '관리자', 410, null, 'ADMIN', true, NOW(), NOW());

/*
  국가별 테스트 계정
*/
INSERT INTO users(password, email, nickname, nation, profile_img, role, term_agreed, created_at, updated_at)
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




 --Area 데이터 삽입
INSERT INTO area
    (area_code, name)
VALUES
    (1, 'SEOUL'),
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
    (37, 'JEONBUKDO'),
    (38, 'JEOLLANAMDO'),
    (39, 'JEJUDO');

-- Place 데이터 삽입
INSERT INTO place (area_code, title, address, content_id, content_type_id, overview, lat, lng, likes, modified_time, api_type, thumbnail_url) VALUES
(1, '63 Square (63스퀘어)', '50, 63-ro, Yeongdeungpo-gu, Seoul', 264122, 76, '63 Square is one of the most recognizable landmarks of Korea and the symbol of its rapid economic growth. Like its name, the building has 63 floors, 60 above-ground and 3 underground. The lobby and the outdoor garden exhibits installations created by contemporary artists. 63 Square is known for its golden glass facade, which puts up different ambiences depending on the season and the amount of sunlight. It is the best place to watch the fireworks from the Seoul International Fireworks Festival.', 37.5198673186, 126.9403285961, 0, '2024-03-05 01:35:26', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/86/2526386_image2_1.jpg'),
(1, 'A Moment in Time - Live Caricature & Gallery (시간을 담다)', '20-1, Samil-daero 8-gil, Jung-gu, Seoul', 2590011, 76, 'A Moment in Time adds in aspects of Korean traditions, customs, and landmarks like hanbok, Gwanghwamun Gate, and Korean mystical animals into caricature artworks, serving as an excellent souvenir for both locals and travelers. The gallery also offers hands-on programs like coloring caricature and traditional folk art. In addition, original design products from A Moment in Time are also available, making great gifts.', 37.5627326749, 126.9901422899, 0, '2021-03-25 11:18:52', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/90/2589890_image2_1.bmp'),
(1, 'Achasan Ecological Park (아차산생태공원)', '127, Walkerhill-ro, Gwangjin-gu, Seoul', 1747653, 76, 'Achasan Ecological Park was established under the Seoul Metropolitan Government''s Five Year Plan for Urban Green Expansion. Through various events and activities, the park provides opportunities to experience and learn about nature and its ecology. Major facilities include Eco Park, Rendezvous Square, Red Clay Road, Barefoot Path, Pine Forest, Mineral Spring, Eco Trail and pergolas.', 37.5519609256, 127.1012880282, 0, '2021-05-14 13:39:19', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/60/741860_image3_1.jpg'),
(1, 'Achasan Mountain (아차산)', 'Acheon-dong, Guri-si, Gyeonggi-do', 1349267, 76, 'Achasan Mountain is a 295m-high mountain that stretches across Seoul and the city of Guri. Its proximity to the city makes it an accessible destination for hiking in Seoul. The hiking trails are maintained quite well, so even beginners can enjoy hiking in the mountain. From the summit of Achasan Mountain, one can enjoy the beautiful view of Seoul and the Hangang River. In fall, the mountain is famous for its fall colors as well.', 37.5529441189, 127.0998297997, 0, '2024-03-07 07:24:28', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/57/2661757_image2_1.jpg'),
(1, 'Achasanseong Fortress (아차산성)', 'Gwangjang-dong, Gwangjin-gu, Seoul-si', 264196, 76, 'Achasanseongseong Fortress boasts magnificent views of the Hangang River and skyline. The 200m-high mountain fortress wall was built to face southeast, towards the Hangang River. It is also called Janghan-seong or Gwangjang-seong. A fierce battle was once fought here among Gogury, Baekje and Silla because of its strategic location during the period of the Three Kingdoms. King Gae-Ro of Baekje (r. 455-475) died in the war by the Goguryeo forces sent by King Jang-Su (r. 413-491) and General Ondal of Goguryeo was also killed in a battle with the Silla army in 590. Achasan Mountain is popular as a citizens'' resting place with a historical trail and a natural park including various facilities such as walkway, mountain walkway, a badminton court, a wrestling range, an archery range, various amusement facilities for children, benches, and pavilion.', 37.5538246631, 127.1048709825, 0, '2023-04-05 12:56:03', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/23/741923_image3_1.jpg'),
(1, 'Amore Seongsu (아모레 성수)', '7 Achasan-ro 11-gil, Seongdong-gu, Seoul', 3078774, 76, 'Amore Seongsu is an experiential store presented by the cosmetics brand Amorepacific Corporation. The building was renovated from an old car repair shop and presents a unique vibe. Visitors can test products on their skin and purchase products from over 30 brands released by Amorepacific Corporation. Recommended items include the Puzzlewood Hand Cream and Vegan Brush, made using a vegan recipe and a certified-vegan. A pop-up store is operated every month in collaboration with brands from various fields worldwide, providing trendy and extraordinary experiences such as exhibitions and product testing.', 37.5446026871, 127.0590698587, 0, '2024-01-05 06:05:08', 'TOUR', ''),
(1, 'AMORE Spa (아모레퍼시픽 스파)', '21, Apgujeong-ro 29-gil, Gangnam-gu, Seoul', 1295190, 76, 'AmorePacific, a Korean brand, initially gained popularity in New York since its opening of AMORE Beauty Gallery & Spa in Soho, New York. The AMORE Spa is a popular spa and treatment center frequented by many international celebrities. The AMORE Spa Seoul branch, opened in April 2005, offers approximately 30 different kinds of programs. One of the most popular spa programs is Spa Therapy, which uses a diverse range of natural therapy materials including lotus flower, ginger and bamboo. Another popular program is the Halla Green Tea program, which uses green tea leaves grown in Jeju''s Hallasan Mountain. Other programs include Gold & Silver Energy Healing therapy, Mystic Bamboo Forest, facial therapies, body slimming and massages.', 37.5286678625, 127.0276620598, 0, '2020-03-26 16:03:52', 'TOUR', ''),
(1, 'Amsa Ecological Park (암사생태공원)', '83-66, Seonsa-ro, Gangdong-gu, Seoul', 1131921, 76, 'Amsa Ecological Park (located in Amsa-dong) is a large scenic park with long winding trails passing by reeds and other beautiful plants of the Han River. It is here as well that visitors can watch the flight of Korea''s migratory birds. The Ecological Park is famous for its beautiful scenery and its lush groves of reeds and pussy willows growing alongside the natural riverside road. Wild roses, loosestrifes, and tiger lilies are in bloom from spring to fall and the observation deck gives visitors a beautiful view of Han River and the area''s wide variety of birds such as reed warblers, titmice, and swallows. The river basin and its surrounding stones are a great place to learn about nature and are home to various bugs such as beetles, river mayflies, big scarlet hairstreak butterflies, and other water insects. The Amsa Ecological Park is located nearby Godeok River Side Ecological Park and the Bicycle Theme Park, making this particular area one of the most popular tourist attractions along the Han River.', 37.5500170857, 127.1222833943, 0, '2017-01-12 10:25:12', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/96/1894496_image3_1.jpg'),
(1, 'Apgujeong Rodeo Street (압구정 로데오거리)', 'Sinsa-dong, Gangnam-gu, Seoul', 264107, 76, 'Apgujeong Rodeo Street is a cultural street in Apgujeong full of luxury brands and multi-shops. It is a popular destination with significant foot traffic thanks to its convenient shopping opportunities, famous cafes and restaurants, and shops with unique interior design. This street is the place to experience Korean fashion and cultural trends. Nearby tourist sites include Cheongdam Fashion Street and Garosu Street of Sinsa-dong.', 37.5268766055, 127.0388971983, 0, '2024-03-18 02:57:57', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/44/3109344_image3_1.JPG'),
(1, 'April 19th National Cemetery (국립4.19민주묘지)', '17, 4.19-ro 8-gil, Gangbuk-gu, Seoul', 1348713, 76, 'Located at the foot of Bukhansan Mountain, the April 19th National Cemetery was established in memory of the 224 people who lost their lives during the 4.19 Revolution in 1960. The cemetery features a memorial hall and a traditional wooden structure that houses the grave of the historic figure Yu Yeongbong. At the cemetery is a pond surrounded by sculptures such as "Symbolic Door," "Roots of Democracy," and "Sparks of Justice" and a memorial tower bearing an inscription for the brave patriots who lost their lives during the revolution. Groves of pine, juniper, yew, and maple trees and the well-kept hiking path add a natural charm to the overwhelming ambiance of peace and serenity. Many people visit to see the spring blossoms in May, and throughout the year to take in the glorious scene of the sunset over Bukhansan Mountain.', 37.6484480504, 127.0091159038, 0, '2023-01-02 05:35:52', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/50/2025950_image3_1.jpg'),
(2, 'Aramaru Observatory (아라마루 전망대(아라폭포))', '79-4 Duksil-dong, Gyeyang-gu, Incheon', 3099850, 76, 'The Aramaru Observatory, a circular structure perched in the gorge section of Gyeyangsan Mountain, occupies the highest point of the Arabaetgil, a maritime path linking the West Sea with the Hangang River. Renowned for offering the most spectacular vista of the entire Arabaetgil, the observatory features a glass floor that provides a stunning overhead view of the Arabaetgil below. At night, the ambiance is further enhanced by the twinkling lights installed along the railings and floor, creating a mesmerizing atmosphere that captivates visitors.', 37.5729068961, 126.7013766786, 0, '2024-02-15 07:56:38', 'TOUR', ''),
(2, 'Baedari Secondhand Bookstore Alley (배다리 헌책방 골목)', '3 Geumgok-ro, Dong-gu, Incheon', 3034038, 76, 'Baedaeri Secondhand Bookstore Alley is a representative location of Incheon''s past and the life of people living here. The street is home to five bookstores that sell books on every topic, from art and music to traditional medicine, children''s literature, and dictionaries. The entrance to the alley also features murals of life in the past. Recently, the street was used as a filming location for the drama "Guardian: The Lonely and Great God (2016)," making it an extremely popular spot to visit.', 37.4727784971, 126.636143758, 0, '2023-11-08 02:04:10', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/27/2601527_image3_1.jpg'),
(2, 'Baemikkumi Sculpture Park (배미꾸미조각공원)', '41, Modo-ro 140beon-gil, Bukdo-myeon, Ongjin-gun, Incheon', 2010149, 76, 'Baemikkumi Sculpture Park rests on Modo Island, one of the nearest islets to Yeongjongdo Island where Incheon International Airport is located. The name ''baemikkumi'' comes from the local dialect, as people say the island resembles the shape of a hole normally seen from the bottom of a ship as ''baemit'' translates to the bilge, and ''kkumi'' is most closely translated to mean ''a hole'' in English. The entire island has now become a beautiful exhibition hall of art works thanks to sculptor Lee Il-ho, who originally started and donated his talents for visitors coming to the island. The art displayed on the island makes for an inspiring contrast to Seohae (west sea, or yellow sea) in the background.', 37.5290929129, 126.4064270725, 0, '2016-10-28 11:21:39', 'TOUR', ''),
(2, 'Baengnyeongdo Island (백령도)', 'Baengnyeong-ro, Ongjin-gun, Incheon', 264301, 76, 'Initially known as Gokdo Island, the name was changed to the current Baengnyeongdo Island based on its appearance as having white wings. It is the eighth largest island in Korea, and the closest to North Korea. Due to its vantage point, visitors must undergo a security process before touring the island. Baengnyeongdo Island is famous for an array of attractions and fresh seafood, with many fish varieties living in the area. The mighty sea cliffs of Dumujin are said to be the last masterpieces of an old god. Additional attractions of the island include the two-story high Simcheonggak Pavilion; Mulgaebawi Rock, the only place in Korea to see seals; and Sagot Coast, one of only two places in world with diatomacheous earth, strong enough to drive on and for military planes to use as a runway.', 37.9644016397, 124.6766833194, 0, '2021-04-23 09:18:53', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/72/1824172_image3_1.jpg'),
(2, 'Boreumdo Island (Spoonbill Eco Village) (볼음도(저어새 생태마을))', '595-1 Boreumdo-ri, Seodo-myeon, Ganghwa-gun, Incheon', 3099756, 76, 'Boreumdo Island, situated in Seodo-myeon, Ganghwa-gun, is renowned as a habitat for spoonbills. The spoonbill, a rare migratory bird with a global population of around 3,000 individuals, finds refuge on this island. Since 2012, Boreumdo Island has been designated as a protected habitat for spoonbills, offering various experiential programs aimed at fostering an understanding of the tidal flat ecosystem. Key experiences include clam digging, net usage, and mudskipper fishing.', 37.6707559982, 126.1919067136, 0, '2024-02-15 06:20:30', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/51/2674851_image3_1.jpg'),
(2, 'Bupyeong Haemultang (Seafood Stew) Street (부평 해물탕거리)', '113, Bupyeongmunhwa-ro, Bupyeong-gu, Incheon', 1553063, 76, 'The history of Bupyeong Haemultang Street began 30-40 years ago, when there were just three restaurants on the street. Over the years, the street  gained a reputation for serving delicious haemultang (seafood stew), and there are now ten seafood restaurants on the street. Each restaurant offers a rich, yet distinctly different flavored seafood stew prepared with aromatic vegetables and fresh seafood purchased from the marketplace every morning. The Haemultang Street has become so popular that many people travel the long distance from Seoul just for a satisfying meal of seafood stew. The restaurants specialize in stew and steamed seafood dishes, but also continue to develop new fusion menus to please all taste buds.', 37.4943692964, 126.7290124625, 0, '2022-10-26 00:48:46', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/09/740109_image3_1.jpg'),
(2, 'Chamseongdan Altar (강화 참성단)', 'Heungwang-ri, Ganghwa-gun, Incheon', 264300, 76, 'Located on the northside of Manisan Mountain peak, Chamseongdan Altar is where Dangun (founder of Korea) is said to have offered sacrifices to the heavens. The story of Dangun reads that he made sacrifices over 4,000 years ago, making this relic a historical treasure. Chamseongdan was renovated in 1270 under Goryeo Wonjong''s and after being renovated several more times. To this day it has remained the same way and kept the same appearance. Chamseongdan is a natural stone 5 meters in x_height, circular in shape at the bottom and rectangular at the top. Because it is in the middle of Baekdusan Mountain and Hallasan Mountain, you can see the islands of the West sea and the inland scenery. It is said that in the past, the kings of Goguryeo, Baekje, and Silla Kingdoms all offered sacrifices to the heavens here.  This form of ceremonial sacrificing continued prevailed until the Joseon Period (1392~1910). Visitors can see from scattered relics how Korea''s ancestors revered and feared the heavens.  Even now, on Gaecheonjeol Day, Koreans offer sacrifices to Dangun here, and for national athletic events, a sacred flame for the games is ignited here. Manisan Mountain is 495m above sea level, which makes it the highest mountain in Ganghwa. The entire area was designated as a National Tour Site in 1977.  Climb to the summit and you can see the entire Gyeonggi area. The path leading up to Chamseongdan especially has a wonderful view of the mountain and the sea, and is a favorite of the climbers.', 37.613966802, 126.4315502058, 0, '2020-06-29 11:20:34', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/41/217641_image3_1.jpg'),
(2, 'Cheongna Lake Park (청라호수공원)', '59 Crystal-ro, Seo-gu, Incheon', 2475443, 76, 'Cheongna Lake Park is a waterfront park located in the heart of Cheongna International City, connected to the river path along the canal. It features three islands and a 4-kilometer circular walking trail that connects four themed zones (Eco Zone, Traditional Zone, Leisure Zone, Art Zone). The park offers attractions such as a large-scale musical fountain, a traditional pavilion like Cheongnaro Pavilion, Outdoor Concert Hall, Fantasy Forest Playground, and Rose Garden. Visitors can also enjoy water leisure sports.', 37.5305967767, 126.6362810266, 0, '2024-03-20 03:31:19', 'TOUR', ''),
(2, 'Daecheongdo Island (대청도)', 'Daecheong-myeon, Ongjin-gun, Incheon', 1370327, 76, 'Daecheongdo Island, with an area of 12.7 square kilometers and 24.7 kilometers in length, houses a little over 1,000 citizens. The island is located 202 kilometers northwest of Incheon and is known for fishing. With most of the villagers being fishermen, the whole island is one large fishing area. The island is also known for clean air and various tourist attractions.', 37.825435063, 124.7026483977, 0, '2021-07-09 14:42:32', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/67/2724467_image2_1.jpg'),
(2, 'Daeijakdo Island (대이작도)', 'Ijak-ri, Ongjin-gun, Incheon', 1751273, 76, 'Ijakdo Island is made up of Daeijakdo Island and Soijakdo Island. In the past, the island was called Ijeokdo Island because it served as a hideout for pirates. The sea, mountain, and shoal exist in harmony. The beauty of Daeijakdo Island comes from the view of the blue ocean from the peak of Buasan Mountain and the shoal that only appears during low tide. The shoal almost reaches Soijakdo Island and creates a large field of sand. The shoal is also called pulchi and only appears during low tide for six hours a day.', 37.1749176125, 126.2587835301, 0, '2020-08-10 17:21:33', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/09/2661909_image2_1.jpg'),
(3, 'Bomunsan Forest Trail (보문산 행복 숲 둘레길)', '197-208, Daesa-dong, Jung-gu, Daejeon', 1752774, 76, 'Jung-gu is the heart of Daejeon Metropolitan City and is home to Bomunsan Mountain and Banghwasan Mountain. Bomunsan Mountain is located in the southern part of the city and was once called "Bomulsan" meaning "treasure mountain" because it was believed that treasure was buried in the mountain. Bomunsan Mountain was designated as a park in 1965. There are many mineral springs and the azaleas and cherry blossoms that bloom in spring. In autumn, the leaves change to a beautiful color in the maple forest. This park has the Bomunsanseong Fortress and Bomunsa Temple Site, an outdoor music hall, an observatory, amusement facilities, a cable car, and about 10 hiking trails, making it a great place to exercise. There are also recreational facilities such as mountain climbing as well as leisure facilities including an indoor roller skating rink in Sajeong Park, soccer field, outdoor music hall, Greenland, and Youth Square. The Bomunsan Forest Trail is a 13-kilometer walking trail that circles Bomunsan Mountain. The dense forest along the trail creats a cool shade to walk under and boasts beautiful fall foliage in autumn.', 36.3092682155, 127.4222435934, 0, '2021-12-24 10:39:57', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/50/2675750_image2_1.jpg'),
(3, 'Daecheongho Lake (대청호)', 'Dong-gu, Daejeon / Munui-myeon, Chungcheongbuk-do', 264343, 76, 'Daecheongho Lake is a man-made lake, stretching from Daejeon to Cheongju-si, Okcheon-gun, and Boeun-gun. Construction began in 1975 and finished in 1980, and the lake supplies water for various use to people in Daejeon and Cheongju. The reservoir water spans an area of over 72.8 ㎢ with a perimeter of 80 kilometers, and the water kept in storage has a volume of 1.5 billion tons, making the reservoir the third largest lake in Korea. The area is famous for its beautiful driving course along a tree-lined road through the nearby mountain which has an altitude of 200 to 300 meters. Also, resident birds and migratory birds, such as white heron, can be seen during summer in the upper region of the lake.', 36.4011148323, 127.5101665667, 0, '2022-12-26 06:36:20', 'TOUR', ''),
(3, 'Daedong Sky Park (대동하늘공원)', '182, Dongdaejeon-ro 110beon-gil, Dong-gu, Daejeon', 2657579, 76, 'Daedong Sky Park was formed as part of the Rainbow Project on December 2009. A giant windmill, which represents the park, is located 127 meters above sea level. Visitors can see an amazing panoramic view of the city as well as relax at one of the many benches and pagodas. A mural village connected to the Sky Park offers themed coffee shops and has become a popular attraction.', 36.3317689426, 127.4515986926, 0, '2022-12-26 02:40:00', 'TOUR', ''),
(3, 'Daejeon Expo Park (대전엑스포과학공원)', '480, Daedeok-daero, Yuseong-gu, Daejeon', 789741, 76, 'Daejeon Expo Park was established following the closing of the Daejeon Expo in 1993. The park comprises various facilities like the Hanbit Tower, the representative tower of Daejeon World Expo; Daejeon Expo Memorial Hall, remodeled to commemorate the Daejeon World Expo; World Expo Memorial Museum, exhibiting the world expo''s souvenir and symbolic objects throughout 200 years of expo history, and Daejeon Traffic Culture Institute where children can learn about traffic safety and the history of transportation.', 36.3775458553, 127.3849895164, 0, '2022-12-26 08:35:51', 'TOUR', ''),
(3, 'Daejeon EXPO Plaza (엑스포시민광장)', '169 , Dunsan-daero, Seo-gu, Daejeon', 2990037, 76, 'Expo Citizen Plaza is a space for visitors to enjoy tourism, cultural life, leisure, and leisure sports. In the square, there is the Hanbat Arboretum. In addition, visitors can participate in or watch various events at an outdoor performance hall with moving shade for the first time in Korea. Well-maintained bike paths and central squares are great for cycling or inline skating. At sunset, the landscape lights of the Expo Bridge light up, so taking a commemorative photo is recommended. There are shops and cafes in the management building of the square where the fountain is installed, so it is perfect for enjoying snacks or light drinks.', 36.3659250159, 127.3879840082, 0, '2023-06-10 07:32:33', 'TOUR', ''),
(3, 'Daejeon Hoedeok Dongchundang Park (대전 회덕 동춘당 공원)', '80, Dongchundang-ro, Daedeok-gu, Daejeon', 1370575, 76, 'Dongchundang Park was once the residence of Song Jung-gil, a noted scholar during the Joseon dynasty. Dongchundang Residential Compound (Treasure) has kept some of its original features including its three ponds, pavilions, old swings, and the nameplate written by Uam Song Si-yeol in 1678 in honor of Song Jung-gil. Today, the park is the main venue of the annual Dongchundang Culture Festival along with other local festivals.', 36.3653510299, 127.4413181434, 0, '2022-12-26 05:21:04', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/05/2364205_image3_1.jpg'),
(3, 'Daejeon Jagwangsa Temple (자광사(대전))', '50-8, Hakhadong-ro 63beon-gil, Yuseong-gu, Daejeon', 1349244, 76, 'Legend has it that the region of Hakha-dong was where the gods lived when they periodically came down from the heavens. When Jagwangsa Temple was built in the district, Song Siyeol saw it as a sign and created a village school near the temple to train his pupils. It was his hope that the sacred land would aide in the raising of gifted students who would grow to serve their community and country. When building the school, Song Siyeol also planted a juniper tree that, 300 years later, still proudly stands by Jagwangsa Temple. Upon entering the temple area, visitors will find to their left the Seongjeonnyeongdangji Memorial Stone, which serves to commemorate Song Siyeol and his school. Next to the memorial stone is a pond in which carp swim lazily around lotus flowers. From the white wooden chair in front of the pond, there is a large bell to the left and main Dharma Hall to the right.', 36.3379018752, 127.3119971207, 0, '2021-09-07 10:09:16', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/29/2689629_image3_1.jpg'),
(3, 'Daejeon National Cemetery Patrotic Footpath (국립대전현충원 보훈둘레길)', '251, Hyeonchungwon-ro, Yuseong-gu, Daejeon', 1752776, 76, 'Daejeon National Cemetery covers a vast area of land spanning 330,000 ㎡. The cemetery is comprised of graves for the deceased patriots, men of national merit, generals, officers, soldiers, and more. Major facilities include the Memorial Tower and Memorial Gate for paying reverence to the deceased patriots, Patriotic Spirit Exhibition Center displaying photos and articles left by the deceased, and an outdoor exhibition space, where military battle equipment is on display. Other facilities within the cemetery grounds include fountains, statues, sculptures, pavilions, and Hyeonchungji Pond, a man-made pond in the shape of the Korean peninsula. In particular, the memorial site''s Patriotic Footpath serves as a great walking path for those who seek to tour the nearby areas on foot. There are a total of seven walking trails that pass by beautiful and meaningful attractions.', 36.3622460736, 127.2985587765, 0, '2021-08-18 14:51:06', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/25/2661825_image2_1.JPG'),
(3, 'Daejeon O-World (대전오월드)', '70, Sajeonggongwon-ro, Jung-gu, Daejeon', 1371161, 76, 'Daejeon O-World was established when the Daejeon Zoo and Joy Land, an amusement park, were integrated under the supervision of the Daejeon City Corporation. The project cost a whopping 40 billion won and resulted in the construction of Flower Land (100,000㎡) in addition to the renamed Zoo Land and the preexisting Joy Land. It opened on May 1, 2009 to the public. The three main sections of Daejeon O-World are Zoo Land, Joy Land, and Flower Land. Zoo Land is currently home to a total of 600 animals of 130 different species including American black bears, Bengal tigers, lions, elephants, giraffes, zebras, and ostriches. Amusement rides, waterslides, and four-season sledding are housed at Joy Land. Flower Land boasts a number of smaller sections such as Rose Garden, Four Season Garden, Herb Garden, and Maze Garden and is home to a total of 150,000 tress of 100 different species and 200,000 flowers of 85 different species. An outdoor stage and concert hall are also located in the area. There are plenty of things to see and enjoy in every corner of Daejeon O-World. Just beyond Festival Street, visitors will find a large (3,000㎡) pond with a fountain.', 36.2873119994, 127.4001294978, 0, '2022-12-27 00:34:45', 'TOUR', ''),
(3, 'Daejeon Observatory (대전시민천문대)', '213-48, Gwahak-ro, Yuseong-gu, Daejeon', 1751277, 76, 'Daejeon Observatory, opened on May 3, 2001, is the first observatory to open to the public in the region. Since opening, over 100,000 visitors visit annually. Various experience programs and events are held throughout the year to inform visitors about constellations and space.', 36.3815392861, 127.3536144373, 0, '2021-06-15 14:55:21', 'TOUR', ''),
(4, 'Aayang Gichatgil (아양기찻길)', '82, Haedong-ro, Dong-gu, Daegu', 2657126, 76, 'After 78 years of operating, Ayang Railroad Bridge was discontinued and turned into a cultural space with a name Ayang Gichatgil. This attraction is 277 meters in length, 14.2 meters in x_height and total area of 427.75㎡. Visitors can enjoy the observatory, exhibitions, cafe and other facilities. The attraction was award Red Dot Design Award for restoring a discontinued railraod bridge using public design. Nearby attractions include Simni Cherry Blossom Road, Gomoryeongbi Monument, Dongchong Resort, and Onggijonggi Happy Village.', 35.8903490289, 128.6383388839, 0, '2021-08-09 15:26:07', 'TOUR', ''),
(4, 'Ancient Tombs in Bullo-dong (대구 불로동 고분군)', 'Bullo-dong, Dong-gu, Daegu', 583576, 76, 'About 200 tombs are located on a hillock in Bullo-dong, Dong-gu near Geumhogang River. The Ancient Tombs in Bullo-dong, which have been designated as Historic Site No. 262, were formed during the Three Kingdoms Period. It is speculated to be a mass burial site of all those who settled and controlled this region during ancient times.', 35.9145547565, 128.6456570076, 0, '2021-06-14 16:35:28', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/57/1929257_image3_1.jpg'),
(4, 'Anjirang Gopchang Alley (안지랑 곱창골목)', '67 Anjirang-ro 16-gil, Nam-gu, Daegu', 1864872, 76, 'Anjirang Market, located halfway between Anjirang Five-way Intersection and Anjiranggol-ro Street, is now more widely known for the alley filled with restaurants serving marinated gopchang (intestines). The area started with Chungbuk Restaurant opening in 1979 and expanded until the end of the IMF crisis in 1998. Locals enjoy coming to the area because of the affordable prices. Recently, the area is also trying to draw in a younger demographic by targeting the younger population. In 2012, Anjirang Gopchang Alley was selected as one of the top five food themed streets in the nation by the Ministry of Culture, Sports and Tourism.', 35.8368983585, 128.5751792685, 0, '2023-11-17 07:43:45', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/37/2363737_image3_1.jpg'),
(4, 'Apsan Cable Car (Apsan Observatory) (앞산 케이블카(앞산 전망대))', '574-114 Apsansunhwan-ro, Nam-gu, Daegu', 2408163, 76, 'Apsan Observatory, located in Nam-gu, is a famous tourist attraction in Daegu that offers a panoramic view of the city. Apsan Observatory''s architecture has been praised for combining the city, nature, history, and future. Besides being a tourist attraction, it also serves as a place where Daegu citizens learn about their city.', 35.8244027708, 128.5871618934, 0, '2023-11-17 01:48:10', 'TOUR', ''),
(4, 'Apsan Cafe Street (앞산카페거리)', '191 Daemyeongnam-ro, Nam-gu, Daegu', 3002086, 76, 'Apsan Cafe Street is filled with cafes of all different types. From houses modified into a cafe, restaurants, and gallery cafes, there are more than 40 coffee shops along the street. Most are cafes serving coffee, sandwichs, and desserts, but there are also restaurants serving pizza, pasta, and steak as well as Japanese food, pubs and pie shops. The street is perfect for couples going on a date or families looking for sweets. Attractions nearby include Apsan Park and Anjirang Gopchang Alley for a place to eat, drink and rest. Anjirang Station (Daegu Subway Line 1) is also 5-10 minutes away on foot.', 35.8358068876, 128.5791150628, 0, '2023-11-17 07:48:11', 'TOUR', ''),
(4, 'Apsan Mountain Haeneomi Observatory (앞산 해넘이전망대)', 'Daemyeong-dong, Nam-gu, Daegu', 2944987, 76, 'Apsan Mountain Haeneomi Observatory, where you can see the scenery of Daegu along with the sunset, is located in Apsan Ppallaeteo Park in Nam-gu. Incorporating the history and symbolism of Apsan Ppallaeteo Park, the observatory''s design embodies laundry wring. The ramp leading to the observatory is perfect for walking as you can see the panoramic view of Apsan Mountain and the surrounding landscape.', 35.8322070451, 128.5655246933, 0, '2023-08-28 05:25:32', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/00/2703800_image2_1.jpg'),
(4, 'Biseulsan Recreational Forest (비슬산자연휴양림)', '99 Iryeonseonsa-gil, Yuga-eup, Dalseong-gun, Daegu', 615831, 76, 'Biseulsan Recreational Forest is located near Daegyeonbong Peak, between Johwabong and Gwanggibong Peaks, and is beautiful all year round. The largest charm of this forest is the ability to enjoy the beautiful scenery in comfort thanks to the amenities. The valley features many uniquely shaped rocks. Every year in late spring, the field near the summit of Biseulsan Mountain blooms with wildflowers. The forest is a popular location for youth retreats during school vacations. Additional facilities include cabins, a campground, pond, and more.', 35.69005271, 128.5129249161, 0, '2024-02-07 01:12:25', 'TOUR', ''),
(4, 'Bongsan Culture Street (봉산문화거리)', '38, Bongsanmunhwa-gil, Jung-gu, Daegu', 1178697, 76, 'Bongsan Culture Street is a landmark culture & arts place in Daegu. The street, which stretches all the way from Daegu Hakwon to Bongsan Yukgeori (six-way intersection), has over 20 galleries of various sizes. It was 1991 when the small alley with several art galleries started to turn into a breeding ground for culture and the arts. Soon after, the street was officially designated ''Bongsan Culture Street'' and subsequently, the roads were neatly paved, artistic street lamps were installed, and streetside gardens were created. With the opening of Bongsan Culture Center and Cultural House for Teens, the street became increasingly frequently by citizens, not just professional artists. At Bongsan Culture Street, artwork os on display throughout the year and every October  (when the Bongsan Art Festival is held) the whole street is filled with artistic passion.', 35.8618042938, 128.5980293412, 0, '2020-03-24 13:35:48', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/41/1026241_image3_1.jpg'),
(4, 'Buinsa Temple (Daegu) (부인사(대구))', '967-28 Palgongsan-ro, Dong-gu, Daegu', 3105357, 76, 'Buinsa Temple is a temple nestled on the southern slopes of Palgongsan Mountain, with a deep historical connection to Queen Seondeok of Silla (580–647). The temple is renowned for preserving the woodblocks of the First Tripitaka Koreana, a collection of Buddhist scriptures carved in the 11th century. The current Buinsa Temple encompasses significant structures, including Daeungjeon Hall, Queen Seondeok''s Memorial Hall, and Samseonggak Shrine. Visitors have the opportunity to appreciate the scenic beauty of nature while exploring the cultural and architectural heritage from the Silla and Goryeo periods.', 35.9949489511, 128.6740022555, 0, '2024-02-23 09:42:52', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/22/1574222_image3_1.jpg'),
(4, 'Daegu Apsan Park (대구앞산공원)', '574-87 Apsansunhwan-ro, Nam-gu, Daegu', 1252669, 82, 'Daegu Apsan Park, as its name suggests, is a mountain park located in front of Daegu. It is a natural park where visitors can take a cable car to the summit observation deck and enjoy a panoramic view of the cityscape below. The park features numerous valleys, springs, as well as landmarks such as the Nakdong River Victory Memorial Hall, Chunghontap tower, youth training center, archery range, riding facilities, children''s playground, library, museum, and botanical garden.', 35.8290189503, 128.5890560428, 0, '2024-03-15 01:49:45', 'TOUR', 'http://tong.visitkorea.or.kr/cms/resource/09/2363709_image3_1.jpg');

--store 데이터 삽입
INSERT INTO store (store_id, contact_number, open_time, place_id)
VALUES
(1, 'contact_number 1', '09:00', 1),
(2, 'contact_number 2', '10:00', 2),
(3, 'contact_number 3', '11:00', 3),
(4, 'contact_number 4', '09:00', 4),
(5, 'contact_number 5', '10:00', 5),
(6, 'contact_number 6', '11:00', 6),
(7, 'contact_number 7', '09:00', 7),
(8, 'contact_number 8', '10:00', 8),
(9, 'contact_number 9', '11:00', 9),
(10, 'contact_number 10', '09:00', 10),
(11, 'contact_number 11', '10:00', 11),
(12, 'contact_number 12', '11:00', 12),
(13, 'contact_number 13', '09:00', 13),
(14, 'contact_number 14', '10:00', 14),
(15, 'contact_number 15', '11:00', 15),
(16, 'contact_number 16', '09:00', 16),
(17, 'contact_number 17', '10:00', 17),
(18, 'contact_number 18', '11:00', 18),
(19, 'contact_number 19', '09:00', 19),
(20, 'contact_number 20', '10:00', 20),
(21, 'contact_number 21', '11:00', 21),
(22, 'contact_number 22', '09:00', 22),
(23, 'contact_number 23', '10:00', 23),
(24, 'contact_number 24', '11:00', 24),
(25, 'contact_number 25', '09:00', 25),
(26, 'contact_number 26', '10:00', 26),
(27, 'contact_number 27', '11:00', 27),
(28, 'contact_number 28', '09:00', 28),
(29, 'contact_number 29', '10:00', 29),
(30, 'contact_number 30', '11:00', 30),
(31, 'contact_number 31', '09:00', 31),
(32, 'contact_number 32', '10:00', 32),
(33, 'contact_number 33', '11:00', 33),
(34, 'contact_number 34', '09:00', 34),
(35, 'contact_number 35', '10:00', 35),
(36, 'contact_number 36', '11:00', 36),
(37, 'contact_number 37', '09:00', 37),
(38, 'contact_number 38', '10:00', 38),
(39, 'contact_number 39', '11:00', 39),
(40, 'contact_number 40', '09:00', 40);

--placeImg 데이터 삽입
INSERT INTO place_img (place_id, img_url)
VALUES
(1, 'http://tong.visitkorea.or.kr/cms/resource/86/2526386_image2_1.jpg'),
(2, 'http://tong.visitkorea.or.kr/cms/resource/90/2589890_image2_1.bmp'),
(3, 'http://tong.visitkorea.or.kr/cms/resource/60/741860_image3_1.jpg'),
(4, 'http://tong.visitkorea.or.kr/cms/resource/57/2661757_image2_1.jpg'),
(5, 'http://tong.visitkorea.or.kr/cms/resource/23/741923_image3_1.jpg'),
(6, ''),
(7, ''),
(8, 'http://tong.visitkorea.or.kr/cms/resource/96/1894496_image3_1.jpg'),
(9, 'http://tong.visitkorea.or.kr/cms/resource/44/3109344_image3_1.JPG'),
(10, 'http://tong.visitkorea.or.kr/cms/resource/50/2025950_image3_1.jpg'),
(11, ''),
(12, 'http://tong.visitkorea.or.kr/cms/resource/27/2601527_image3_1.jpg'),
(13, ''),
(14, 'http://tong.visitkorea.or.kr/cms/resource/72/1824172_image3_1.jpg'),
(15, 'http://tong.visitkorea.or.kr/cms/resource/51/2674851_image3_1.jpg'),
(16, 'http://tong.visitkorea.or.kr/cms/resource/09/740109_image3_1.jpg'),
(17, 'http://tong.visitkorea.or.kr/cms/resource/41/217641_image3_1.jpg'),
(18, ''),
(19, 'http://tong.visitkorea.or.kr/cms/resource/67/2724467_image2_1.jpg'),
(20, 'http://tong.visitkorea.or.kr/cms/resource/09/2661909_image2_1.jpg'),
(21, 'http://tong.visitkorea.or.kr/cms/resource/50/2675750_image2_1.jpg'),
(22, ''),
(23, ''),
(24, ''),
(25, ''),
(26, 'http://tong.visitkorea.or.kr/cms/resource/05/2364205_image3_1.jpg'),
(27, 'http://tong.visitkorea.or.kr/cms/resource/29/2689629_image3_1.jpg'),
(28, 'http://tong.visitkorea.or.kr/cms/resource/25/2661825_image2_1.JPG'),
(29, ''),
(30, ''),
(31, ''),
(32, 'http://tong.visitkorea.or.kr/cms/resource/57/1929257_image3_1.jpg'),
(33, 'http://tong.visitkorea.or.kr/cms/resource/37/2363737_image3_1.jpg'),
(34, ''),
(35, ''),
(36, 'http://tong.visitkorea.or.kr/cms/resource/00/2703800_image2_1.jpg'),
(37, ''),
(38, 'http://tong.visitkorea.or.kr/cms/resource/41/1026241_image3_1.jpg'),
(39, 'http://tong.visitkorea.or.kr/cms/resource/22/1574222_image3_1.jpg'),
(40, 'http://tong.visitkorea.or.kr/cms/resource/09/2363709_image3_1.jpg');
---- Plan 데이터 삽입
--INSERT INTO plans (id, user_id, area_code, status, start_day, end_day, subject, over_view, created_at, updated_at) VALUES
--(1, 1, 1, 'BEGIN', '2023-09-01', '2023-09-03', '서울 역사 탐방', '서울의 역사적 명소를 둘러보는 3일 여행', '2023-08-09 10:00:00', '2023-08-09 10:00:00'),
--(2, 2, 2, 'BEGIN', '2023-09-15', '2023-09-18', '제주도 자연 체험', '제주도의 아름다운 자연을 만끽하는 4일 여행', '2023-08-09 10:00:00', '2023-08-09 10:00:00'),
--(3, 3, 3, 'BEGIN', '2023-10-01', '2023-10-03', '부산 먹방 여행', '부산의 맛있는 음식과 명소를 즐기는 3일 여행', '2023-08-09 10:00:00', '2023-08-09 10:00:00'),
--(4, 4, 4, 'BEGIN', '2023-10-15', '2023-10-17', '강원도 힐링 여행', '강원도의 아름다운 자연에서 휴식을 취하는 3일 여행', '2023-08-09 10:00:00', '2023-08-09 10:00:00');
--
---- Template 데이터 삽입
--INSERT INTO templates (id, plan_id, place_id, days, orders, move_time, place_summary, reasoning) VALUES
---- 서울 역사 탐방
--(1, 1, 1, 1, 1, 0, '조선 왕조의 법궁인 경복궁 관람', '서울 역사 탐방의 시작으로 조선의 중심이었던 경복궁을 방문합니다.'),
--(2, 1, 4, 1, 2, 30, '전통 한옥 마을인 북촌한옥마을 산책', '경복궁과 가까운 북촌한옥마을에서 전통 가옥을 구경하며 한국의 전통을 체험합니다.'),
--(3, 1, 3, 1, 3, 20, '현대적인 서울의 모습을 볼 수 있는 명동 쇼핑', '역사 탐방 후 현대 서울의 모습을 볼 수 있는 명동에서 쇼핑을 즐깁니다.'),
--(4, 1, 2, 2, 1, 30, '서울의 전경을 볼 수 있는 남산서울타워 방문', '서울의 과거와 현재를 한눈에 볼 수 있는 남산서울타워에서 서울의 전경을 감상합니다.'),
--
---- 제주도 자연 체험
--(5, 2, 5, 1, 1, 0, '유네스코 세계자연유산인 성산일출봉 등반', '제주도의 상징적인 자연 명소인 성산일출봉에서 시작하여 제주의 아름다운 경관을 감상합니다.'),
--(6, 2, 8, 1, 2, 60, '우도에서 자전거 투어', '성산일출봉 근처의 우도로 이동하여 자전거를 타고 섬을 일주하며 제주의 해안 풍경을 즐깁니다.'),
--(7, 2, 6, 2, 1, 90, '중문관광단지에서 해변 휴식', '중문관광단지의 아름다운 해변에서 휴식을 취하고 다양한 해양 활동을 즐깁니다.'),
--(8, 2, 7, 3, 1, 60, '한라산국립공원 트레킹', '제주의 중심인 한라산을 등반하며 제주의 자연을 만끽합니다.'),
--
---- 부산 먹방 여행
--(9, 3, 9, 1, 1, 0, '해운대 해수욕장에서 아침 산책', '부산 여행의 시작으로 유명한 해운대 해변에서 상쾌한 아침 산책을 즐깁니다.'),
--(10, 3, 11, 1, 2, 40, '자갈치시장에서 신선한 해산물 점심', '부산의 대표적인 수산시장인 자갈치시장에서 신선한 해산물로 점심을 즐깁니다.'),
--(11, 3, 10, 2, 1, 30, '감천문화마을 탐방', '알록달록한 감천문화마을을 둘러보며 부산의 문화적 면모를 체험합니다.'),
--(12, 3, 12, 2, 2, 40, '태종대에서 해안 절경 감상', '태종대에서 부산의 아름다운 해안 절경을 감상하며 여행을 마무리합니다.'),
--
---- 강원도 힐링 여행
--(13, 4, 13, 1, 1, 0, '속초해변에서 일출 감상', '강원도 여행의 시작으로 속초 해변에서 아름다운 일출을 감상합니다.'),
--(14, 4, 14, 1, 2, 30, '설악산국립공원 산책', '설악산의 아름다운 계곡과 폭포를 감상하며 가벼운 산책을 즐깁니다.'),
--(15, 4, 15, 2, 1, 120, '남이섬에서 숲 체험', '아름다운 남이섬에서 울창한 숲을 산책하고 다양한 문화 체험을 즐깁니다.'),
--(16, 4, 16, 3, 1, 90, '강릉 경포대에서 월출 감상', '강릉 경포대에서 아름다운 동해의 월출을 감상하며 여행을 마무리합니다.');