use schedule_db;

CREATE TABLE schedule(
                         id BIGINT AUTO_INCREMENT PRIMARY KEY KEY COMMENT '일정 식별자',
                         todo VARCHAR(100) NOT NULL COMMENT '할일 내용',
                         author VARCHAR(100) NOT NULL COMMENT '작성자명',
                         password VARCHAR(100) NOT NULL COMMENT '비밀번호',
                         created_at DATETIME NOT NULL COMMENT '작성일시',
                         modified_at DATETIME NOT NULL COMMENT '수정일시'

);