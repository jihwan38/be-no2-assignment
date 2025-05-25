# 일정관리 앱 서버

## API 명세서

### Schedule API

| 기능             | Method | URL                   | Request 예시                                                                 | Response 예시                                                                                      | 상태코드           |
|------------------|--------|------------------------|------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| 일정 생성         | POST   | /schedules             | `{ "todo": "스터디 준비", "authorId": 1, "password": "1234" }`               | `{ "id": 1, "todo": "스터디 준비", "authorId": 1, "createdAt": "...", "modifiedAt": "..." }`         | 201 Created         |
| 전체 일정 조회     | GET    | /schedules             | `/schedules?authorId=1&modifiedAt=2025-05-25`                                | `[ { "id": 1, "todo": "...", "authorId": 1, "createdAt": "...", "modifiedAt": "..." }, ... ]`        | 200 OK              |
| 선택 일정 조회     | GET    | /schedules/{id}        | 없음                                                                         | `{ "id": 1, "todo": "...", "authorId": 1, "createdAt": "...", "modifiedAt": "..." }`                | 200 OK       |
| 일정 수정         | PUT    | /schedules/{id}        | `{ "todo": "스터디 수정", "authorId": 1, "password": "1234" }`               | `{ "id": 1, "todo": "스터디 수정", "authorId": 1, "createdAt": "...", "modifiedAt": "..." }`         | 200 OK              |
| 일정 삭제         | DELETE | /schedules/{id}        | `{ "password": "1234" }`                        | 없음                                                                                               | 204 No Content |

### Author API

| 기능               | Method | URL                  | Request 예시                                           | Response 예시                                                                                      | 상태코드            |
|--------------------|--------|-----------------------|----------------------------------------------------------|------------------------------------------------------------------------------------------------------|----------------------|
| 작성자 등록         | POST   | /authors              | `{ "name": "홍길동", "email": "hong@example.com" }`       | `{ "id": 1, "name": "홍길동", "email": "hong@example.com", "createdAt": "...", "modifiedAt": "..." }`| 201 Created          |
| 전체 작성자 조회     | GET    | /authors              | 없음                                                     | `[ { "id": 1, "name": "...", "email": "...", "createdAt": "...", "modifiedAt": "..." }, ... ]`      | 200 OK               |
| 선택 작성자 조회     | GET    | /authors/{id}         | 없음                                                     | `{ "id": 1, "name": "...", "email": "...", "createdAt": "...", "modifiedAt": "..." }`               | 200 OK   |
| 작성자 수정         | PUT    | /authors/{id}         | `{ "name": "수정된 이름", "email": "new@email.com" }`     | `{ "id": 1, "name": "...", "email": "...", "createdAt": "...", "modifiedAt": "..." }`               | 200 OK               |
| 작성자 삭제         | DELETE | /authors/{id}         | 없음                                                     | 없음                                                                                               | 204 No Content        |


## ERD 
![ERD](./images/erd.png)


