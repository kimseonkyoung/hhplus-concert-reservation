```mermaid
erDiagram
    USER {
        BIGINT user_id PK
        VARCHAR(100) user_name
    }

    CONCERT {
        BIGINT concert_id PK
        VARCHAR(200) concert_name
    }

    CONCERT_SCHEDULE {
        BIGINT concert_schedule_id PK
        BIGINT concert_id
        DATE play_date
    }

    SEAT {
        BIGINT seat_id PK
        VARCHAR seat_no
        DECIMAL seat_price
    }

    QUEUE {
        BIGINT queue_id PK
        VARCHAR(255) token_uuid
        VARCHAR(20) status
        TIMESTAMP created_at
        TIMESTAMP expires_at
    }

    BALANCE {
        BIGINT user_id
        INT value
    }

    RESERVATION {
        BIGINT reservation_id PK
        BIGINT user_id
        BIGINT concert_id
        BIGINT seat_id
        VARCHAR(20) status
        TIMESTAMP created_at
        TIMESTAMP expires_at
        TIMESTAMP confirm_at
    }

    PAYMENT {
        BIGINT payment_id PK
        BIGINT user_id
        BIGINT concert_schedule_id
    }


    %% Relationships
    CONCERT_SCHEDULE ||--|| CONCERT : "has CONCERT"
    BALANCE ||--o{ USER : "belongs to"
    RESERVATION ||--o{ USER : "made by"
    RESERVATION ||--o{ CONCERT : "for Concert"
    RESERVATION ||--o{ SEAT : "on seat"
    PAYMENT ||--o{ USER : "paid by"
    PAYMENT ||--o{ CONCERT_SCHEDULE : "for schedule"


```


```

-- User 테이블
CREATE TABLE User (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL
);

-- Concert 테이블
CREATE TABLE Concert (
    concert_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    concert_name VARCHAR(200) NOT NULL
);

-- Concert_Schedule 테이블
CREATE TABLE Concert_Schedule (
    concert_schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    concert_id BIGINT UNIQUE NOT NULL,
    play_date DATE NOT NULL
);

-- Seat 테이블
CREATE TABLE Seat (
    seat_id BIGINT PRIMARY KEY,
    seat_no VARCHAR(20) NOT NULL,
    seat_price DECIMAL(10, 2) NOT NULL,
);

-- Queue 테이블
CREATE TABLE Queue (
    queue_id BIGINT AUTO_INCREMENT PRIMARY KEY,      -- 대기열 ID
    token_uuid VARCHAR(255) NOT NULL UNIQUE,         -- 대기열 토큰 (UUID)
	status VARCHAR(20) NOT NULL,                      -- 상태 ID (WAITING, ACTIVE, EXPIRED)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 대기열 진입 시간
    expires_at TIMESTAMP DEFAULT NULL,              -- 대기열 만료 시간
);

-- Balance 테이블
CREATE TABLE Balance (
    user_id BIGINT NOT NULL,
    value INT NOT NULL
);

-- Reservation 테이블
CREATE TABLE Reservation (
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    concert_id BIGINT NOT NULL, 
    seat_id BIGINT NOT NULL
	status VARCHAR(20) NOT NULL,                      -- 상태 ID (PROGRESS, COMPLETED)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 좌석 임시 예매 잠금 시간
    expires_at TIMESTAMP DEFAULT NULL,              -- 좌석 임시 예매 해제 시간
	confirm_at TIMESTAMP DEFAULT NULL				-- 좌석 예약 확정 시간
);

-- Payment 테이블
CREATE TABLE Payment (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    concert_schedule_id BIGINT NOT NULL,
);

```