# API 명세서

## 1. 토큰 발급 API
- **URL**: `/token`
- **Method**: `GET`
- **Description**: 토큰을 생성/재발급 받습니다.
- **Request Parameters**: 없음
- **Response**:
    ```json
    {
      "uuid": 4536783645345,
      "remainingTime": 300
    }
    ```

## 2.콘서트 목록 조회
- **URL**: `/concert`
- **Method**: `GET`
- **Description**: 콘서트 목록을 조회합니다.
- **Request Parameters**: 없음
- **Response**:
    ```json
  {
    "totalCount": 10,
    "currentPage": 1,
    "data": [
      {
       "concertId": 1,
       "concertName": "에일리의 콘서트"
      },
      {
        "concertId": 2,
        "concertName": "김연자의 콘서트"
      },
      {
       "concertId": 3,
       "concertName": "실리카겔 콘서트"
     }
   ]
  }
    ```

## 3. 콘서트 공연 날짜 목록 조회
- **URL**: `/concert/availableDates`
- **Method**: `GET`
- **Description**: 해당 콘서트의 공연 날짜를 조회합니다..
- **Request Parameters**: 
    - `concertId` (쿼리 스트링): 콘서트의 id
- **Response**:
    ```json
    {
    "date": [
      "2025-01-01",
      "2025-01-02",
      "2025-01-03",
      "2025-01-04"
      ]
   }
    ```


## 4. 콘서트 좌석 조회
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

## 5. 좌석 예약
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

## 6. 유저의 잔액 조회
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
## 7. 유저 잔액 충전
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