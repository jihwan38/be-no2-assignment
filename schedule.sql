use schedule_db;

DROP TABLE IF EXISTS schedule;

CREATE TABLE schedule(
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
                         todo VARCHAR(100) NOT NULL COMMENT '할일 내용',
                         author VARCHAR(100) NOT NULL COMMENT '작성자명',
                         password VARCHAR(100) NOT NULL COMMENT '비밀번호',
                         createdAt DATETIME NOT NULL COMMENT '작성일시',
                         modifiedAt DATETIME NOT NULL COMMENT '수정일시'

);






