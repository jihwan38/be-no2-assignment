use schedule_db;

DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS author;

CREATE TABLE author(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    createdAt DATETIME NOT NULL,
    modifiedAt DATETIME NOT NULL
);

CREATE TABLE schedule(
                         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
                         todo VARCHAR(100) NOT NULL COMMENT '할일 내용',
                         authorId BIGINT NOT NULL COMMENT '작성자 식별자',
                         password VARCHAR(100) NOT NULL COMMENT '작성 글 비밀번호',
                         createdAt DATETIME NOT NULL COMMENT '작성일시',
                         modifiedAt DATETIME NOT NULL COMMENT '수정일시',
                         FOREIGN KEY (authorId) REFERENCES author(id) ON DELETE CASCADE
);






