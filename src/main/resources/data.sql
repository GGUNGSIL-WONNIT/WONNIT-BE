-- MySQL 데이터베이스용 INSERT ON DUPLICATE KEY UPDATE 구문 (UUID 포함)
INSERT INTO users (id, name, phone_number, created_at, updated_at)
VALUES (UNHEX(REPLACE('028195e0-6999-137d-a747-0a02b343a12e', '-', '')), 'tester', '010-0000-0000', NOW(), NOW())
ON DUPLICATE KEY UPDATE name         = VALUES(name),
                        phone_number = VALUES(phone_number);


INSERT INTO columns (id, title, subtitle, url, thumbnail_url, created_at, updated_at)
VALUES (UNHEX(REPLACE('019195e0-6999-199b-a747-0a02b343a12e', '-', '')), '【익산칼럼】 삶이 예술이 되는 공간 유휴공간 문화재생',
        '유휴공간, 상상의 출발점', 'http://iksannews.com/default/index_view_page.php?part_idx=243&idx=47440',
        'http://iksannews.com/data/newsThumb/1582278601ADD_thumb180.jpg', NOW(), NOW())
ON DUPLICATE KEY UPDATE subtitle      = VALUES(subtitle),
                        url           = VALUES(url),
                        thumbnail_url = VALUES(thumbnail_url),
                        updated_at    = NOW();

INSERT INTO columns (id, title, subtitle, url, thumbnail_url, created_at, updated_at)
VALUES (UNHEX(REPLACE('019195e0-6c9f-1d9c-b33d-22471e75b18e', '-', '')), '버려진 공간을 문화로 채우다 ‘유휴공간 문화재생 사업’',
        '지역의 장소, 문화적 가치를 담고 있는 유휴공간을 문화로 재창조', 'https://kukak21.com/bbs/board.php?bo_table=news&wr_id=34377',
        'http://www.kukak21.com/data/editor/2308/20230821152319_1336f8b47278baf72f3527070e16c2cc_4f88.jpg', NOW(),
        NOW())
ON DUPLICATE KEY UPDATE subtitle      = VALUES(subtitle),
                        url           = VALUES(url),
                        thumbnail_url = VALUES(thumbnail_url),
                        updated_at    = NOW();

INSERT INTO columns (id, title, subtitle, url, thumbnail_url, created_at, updated_at)
VALUES (UNHEX(REPLACE('019195e0-6f43-2132-8f38-369124d3c589', '-', '')), '노원구, 학교 유휴 시설을 문화예술 공간으로 단장',
        '학교 유휴공간, 학생·주민을 위한 문화예술 플랫폼으로 재탄생', 'https://www.seouland.com/arti/society/society_general/5477.html',
        'https://img.hani.co.kr/imgdb/resize/2019/0726/156403294913_20190726.JPG', NOW(), NOW())
ON DUPLICATE KEY UPDATE subtitle      = VALUES(subtitle),
                        url           = VALUES(url),
                        thumbnail_url = VALUES(thumbnail_url),
                        updated_at    = NOW();