package kr.hhplus.be.server.integration;

/**
 * 차후 통합테스트를 진행해야합니다.
 * 결제 API 테스트:
 * 1) 결제 성공시 잔액 차감 -> 예약 확정 -> 좌석 상태 변경(COMPETED) ->  토큰 상태 변경(EXPIRED) -> 예약 상태 변경(CONFIRMED)
 * 2) 실패시 -> Transactional 롤백 -> 좌석, 예약 상태 롤백 -> 토큰 만료(EXPIRED)
 */
public class PaymentIntegrationTest {

}
