## 패키지 구조

````
com.example.concertreservation
├─ domain
│   ├─ user
│   │   ├─ User.java
│   │   ├─ UserRepository.java             // interface repository
│   │   └─ ...
│   ├─ concert
│   │   ├─ Concert.java
│   │   ├─ ConcertRepository.java         // interface repository
│   │   └─ ...
│   ├─ reservation
│   │   ├─ Reservation.java
│   │   ├─ ReservationRepository.java     // interface repository
│   │   └─ ...
│   └─ token
│       ├─ Token.java
│       ├─ TokenRepository.java           // interface repository
│       └─ ...
├─ infrastructure
│   ├─ orm
│   │   ├─ user
│   │   │   └─ JpaUserRepository.java       // extends JpaRepository
│   │   ├─ concert
│   │   │   └─ JpaConcertRepository.java
│   │   ├─ reservation
│   │   │   └─ JpaReservationRepository.java
│   │   └─ token
│   │       └─ JpaTokenRepository.java
│   ├─ repository
│   │   ├─ UserRepositoryImpl.java      // domain 의 UserRepository 구현
│   │   ├─ ConcertRepositoryImpl.java
│   │   ├─ ReservationRepositoryImpl.java
│   │   └─ TokenRepositoryImpl.java
│   ├─ config
│   │   ├─ InterceptorConfig.java          // TokenInterceptor 등록
│   │   ├─ SchedulerConfig.java            // 스케줄러 설정
│   │   ├─ WebSocketConfig.java            // WebSocket(STOMP 등) 설정
│   │   └─ ...
│   ├─ interceptor
│   │   └─ TokenInterceptor.java           // 토큰 생성·재발급 Interceptor
│   ├─ scheduler
│   │   ├─ TokenExpirationScheduler.java   // 토큰 만료 로직
│   │   ├─ TokenActivationScheduler.java   // 토큰 WAIT→ACTIVE 로직
│   │   └─ SeatLockManagerScheduler.java   // 좌석 임시잠금 5분 후 해제
│   └─ websocket
│       └─ SeatNotificationHandler.java    // WebSocket 메시지 처리
├─ application
│   ├─ facade
│   │   └─ ReservationFacade.java
│   ├─ service
│   │   ├─ UserService.java
│   │   ├─ ConcertService.java
│   │   ├─ ReservationService.java
│   │   ├─ TokenService.java
│   │   └─ dto                             // Service DTO 모음
│   │       ├─ UserDto.java
│   │       ├─ ConcertDto.java
│   │       ├─ ReservationDto.java
│   │       └─ TokenDto.java
│   ├─ mapper
│   │   ├─ facade                         // (Controller DTO ↔ Service DTO)
│   │   │   ├─ UserDtoConverter.java
│   │   │   ├─ ConcertDtoConverter.java
│   │   │   ├─ ReservationDtoConverter.java
│   │   │   └─ TokenDtoConverter.java
│   │   └─ service                        // (Service DTO ↔ Entity)
│   │       ├─ UserEntityConverter.java
│   │       ├─ ConcertEntityConverter.java
│   │       ├─ ReservationEntityConverter.java
│   │       └─ TokenEntityConverter.java
├─ common
│   └─ exception
│       ├─ GlobalExceptionHandler.java    // @RestControllerAdvice
│       ├─ CustomException.java
│       └─ ErrorCode.java
└─ interfaces
└─ api
├─ controller
│   ├─ UserController.java
│   ├─ ConcertController.java
│   ├─ ReservationController.java
│   └─ TokenController.java
└─ dto                            // Controller DTO (외부 요청/응답)
│   ├─ BalanceChargeRequest.java
│   ├─ BalanceResponse.java
│   ├─ ConcertResponse.java
│   ├─ ReservationRequest.java
│   ├─ ReservationResponse.java
│   ├─ SeatResponse.java
└─  └─ TokenResponse.java
````


