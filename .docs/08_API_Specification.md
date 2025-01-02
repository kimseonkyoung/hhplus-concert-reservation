# API 명세서

## 1. 콘서트 공연 날짜 목록 조회
- **URL**: `/concert/availableDates`
- **Method**: `GET`
- **Description**: 해당 콘서트의 공연 날짜를 조회합니다..
- **Request Parameters**: 없음
- **Response**:
    ```json
    [
      {
        "date": "2025-01-01",
        "date": "2025-01-02",
        "date": "2025-01-03",
        "date": "2025-01-04"
      }
    ]
    ```

## 2. 콘서트 좌석 조회
- **URL**: `/concerts/seats`
- **Method**: `GET`
- **Description**: 특정 콘서트의 좌석 정보를 조회합니다.
- **Request Parameters**:
    - `date` (쿼리 스트링): 콘서트의 date
- **Response**:
    ```json
    {
      "seats": [
        {
          "seatId": 1,
          "seatNo": "A1",
          "status": true
        },
        {
          "seatId": 2,
          "seatNo": "A2",
          "status": false
        },
        {
          "seatId": 3,
          "seatNo": "A3",
          "status": false
        },
        {
          "seatId": 4,
          "seatNo": "A4",
          "status": true
        }
      ]
    }
    ```

## 3. 좌석 예약
- **URL**: `/reservations`
- **Method**: `POST`
- **Description**: 좌석을 예약합니다.
- **Request**:
    ```json
    {
      "seatId": 1,
      "userId": "user123",
      "userUuid": "123e4567-e89b-12d3-a456-426655440000"
    }
    ```
- **Response**:
    ```json
    {
      "reservationId": 1,
      "seatId": 1,
      "status": "SUCCESS"
    }
    ```

## 4. 대기열 순번 및 대기열 진입 여부 조회
- **URL**: `/queue/status`
- **Method**: `GET`
- **Description**: 유저의 대기 순번을 조회합니다.
- **Request Parameters**:
    - `uuid` (쿼리 스트링): 해당 유저의 토큰 UUID
- **Response**:
    ```json
    {
      "tokenUuid": "cancelled",
      "position": 0,
      "status": "ACTIVE"
    }
  ```

## 5. 유저의 잔액 조회
- **URL**: `/user/balance`
- **Method**: `GET`
- **Description**:유저의 잔액을 조회합니다.
- **Request Parameters**:
  - `userId` (쿼리 스트링): 해당 유저의 ID
- **Response**:
    ```json
    {
      "userId": 1,
      "balance": 10000
    }
    ```
## 6. 유저 잔액 충전
- **URL**: `/user/balance/charge`
- **Method**: `POST`
- **Description**: 유저의 잔액을 충전합니다.
- **Request Parameters**:
  - `uuid` (쿼리 스트링): UUID
- **Request**:
    ```json
    {
      "userId": 1,
      "amount": 5000
    }
    ```
- **Response**:
    ```json
    {
      "userId": 1,
      "balance": 15000
    }
    ```