# 일정관리 앱 서버

## API 명세서
ScheduleController
### 📌 Schedule API

| 기능             | Method | URL                   | Request 예시                                                                 | Response 예시                                                                                      | 상태코드           |
|------------------|--------|------------------------|------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| 일정 생성         | POST   | /schedules             | `{ "todo": "스터디 준비", "authorId": 1, "password": "1234" }`               | `{ "id": 1, "todo": "스터디 준비", "authorId": 1, "createdAt": "...", "modifiedAt": "..." }`         | 201 Created         |
| 전체 일정 조회     | GET    | /schedules             | `/schedules?authorId=1&modifiedAt=2025-05-25`                                | `[ { "id": 1, "todo": "...", "authorId": 1, "createdAt": "...", "modifiedAt": "..." }, ... ]`        | 200 OK              |
| 선택 일정 조회     | GET    | /schedules/{id}        | 없음                                                                         | `{ "id": 1, "todo": "...", "authorId": 1, "createdAt": "...", "modifiedAt": "..." }`                | 200 OK / 404 Not Found |
| 일정 수정         | PUT    | /schedules/{id}        | `{ "todo": "스터디 수정", "authorId": 1, "password": "1234" }`               | `{ "id": 1, "todo": "스터디 수정", "authorId": 1, "createdAt": "...", "modifiedAt": "..." }`         | 200 OK / 403 Forbidden |
| 일정 삭제         | DELETE | /schedules/{id}        | `{ "todo": "...", "authorId": 1, "password": "1234" }`                        | 없음                                                                                               | 204 No Content / 403 Forbidden |


## ERD 
![ERD](./images/erd.png)


