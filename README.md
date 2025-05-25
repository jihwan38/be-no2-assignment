# 일정관리 앱 서버

## API 명세서

| 기능           | Method | URL               | Request 예시                                                                 | Response 예시                                                                 | 상태코드  |
|----------------|--------|--------------------|------------------------------------------------------------------------------|--------------------------------------------------------------------------------|------------|
| 일정 생성       | POST   | /api/schedules     | `{ "todo": "할 일", "author": "홍길동", "password": "1234" }`                | `{ "id": 1, "todo": "할 일", "author": "홍길동", "createdAt": "...", ... }`    | 201 Created |
| 전체 일정 조회  | GET    | /api/schedules     | `/api/schedules?author=홍길동&modifiedAt=2025-05-25`                          | `[ { "id": 1, "todo": "...", ... }, { ... } ]`                                  | 200 OK     |
| 단일 일정 조회  | GET    | /api/schedules/1   | 없음                                                                         | `{ "id": 1, "todo": "...", ... }`                                              | 200 OK / 404 Not Found |
| 일정 수정       | PUT    | /api/schedules/1   | `{ "todo": "수정 내용", "author": "홍길동", "password": "1234" }`             | `{ "id": 1, "todo": "...", "modifiedAt": "...", ... }`                         | 200 OK / 403 Forbidden |
| 일정 삭제       | DELETE | /api/schedules/1   | `{ "password": "1234" }`                                                     | `{ "message": "삭제 성공" }`                                                  | 204 No Content / 403 Forbidden |


## ERD 
![ERD](./images/erd.png)


