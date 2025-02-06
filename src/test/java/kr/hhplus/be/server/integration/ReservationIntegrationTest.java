package kr.hhplus.be.server.integration;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.reservation.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 예약 API 테스트:
 * 이미 해당 요청시 요청하는 토큰들은 ACTIVE 상태인 것을 전제로 테스트.
 * 1) 한 자리에 다수(3명 이상) 동시 요청시 -> 한 명 성공, 나머지 실패
 */
@SpringBootTest
@Transactional
public class ReservationIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    @DisplayName("한 자리에 3명 이상 동시 요청시 한 명 성공, 나머지 실패")
    void test1() {





    }


}
