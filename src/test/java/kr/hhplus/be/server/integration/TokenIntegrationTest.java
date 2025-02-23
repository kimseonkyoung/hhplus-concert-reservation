package kr.hhplus.be.server.integration;

import kr.hhplus.be.server.application.usecase.ConcertReservationFacade;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.interfaces.api.controller.TokenController;
import kr.hhplus.be.server.interfaces.api.dto.TokenResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 차후 통합테스트를 진행해야합니다.
 * 토큰 발급 테스트:
 * 1) 현재 ACIVE 상태인 토큰은 0개이며, 토큰을 1개 발급시 바로 'ACTIVE' 상태로 토큰을 반환한다.
 * 2) 현재 ACIVE 상태인 토큰은 0개이며, 토큰 생성 연속 2번 요청시 토큰 '2개'의 값은 'ACTIVE' 이다.
 * 3) 현재 ACTIVE 상태인 토큰은 50개이며, 대기열 토큰 진입 'WAIT' 와 순번을 반환한다.
 * 4) 현재 ACTVIE 상태인 토큰이 48개일시, 3의 유저가 요청했을 경우 2개만 'ACTIVE' 로 반환하고 나머지는 'WAIT' 후 순번을 반환한다.
 * 5) 현재 ACTIVE인 토큰이 32개일시, 100명의 유저가 요청했을 경우 18명만 'ACTIVE' 로 반환하고 나머지는 'WAIT' 후 순번을 반환한다.
 */
@SpringBootTest
public class TokenIntegrationTest {

    @Autowired
    private ConcertReservationFacade concertReservationFacade;

    @Autowired
    private TokenController tokenController;

    @Autowired
    private RedissonClient redissonClient;

    @AfterEach
    public void afterEach() {
        tokenController.resetTokens();
    }

    @Test
    @DisplayName("현재 테이블에 ACTIVE 토큰은 없고, 토큰 생성 한 번 요청시 ACTIVE인 토큰 반환")
    void test1() {
        // 토큰 생성 요청
        TokenResponse response = concertReservationFacade.createToken(1L);
        // 검증
        assertEquals(response.getStatus(), TokenStatus.ACTIVE);
    }

    @Test
    @DisplayName("현재 테이블에 ACTIVE 토큰은 없고, 토큰 생성 두 번 요청시 ACTIVE인 토큰 반환")
    void test2() {
        // 토큰 생성 요청
        TokenResponse response = concertReservationFacade.createToken(1L);
        TokenResponse response2 = concertReservationFacade.createToken(2L);
        // 검증
        assertEquals(response.getStatus(), TokenStatus.ACTIVE);
        assertEquals(response2.getStatus(), TokenStatus.ACTIVE);
    }

    @Test
    @DisplayName("현재 테이블에 ACTIVE 토큰은 50개이며, 토큰 생성 요청시 ACTIVE인 토큰 반환")
    void test3() {
        // ACTIVE 토큰 50개 생성
        for (int i = 1; i <= 50; i++) {
            concertReservationFacade.createToken((long) i);
        }

        // 51번째 토큰 요청 -> WAIT 상태 예상
        TokenResponse response = concertReservationFacade.createToken(51L);
        assertEquals(response.getStatus(), TokenStatus.WAIT);
        assertEquals(response.getPosition(), 1);
        System.out.println("실제 토큰 상태값: "+ response.getStatus());
        System.out.println("실제 순번: " + response.getPosition());
    }

    @Test
    @DisplayName("현재 테이블에 ACTIVE 토큰은 48개이며, 3명이 토큰 생성 요청시 2명는 ACTIVE인 1명는 WAIT 토큰 반환")
    void test4() {
        // ACTIVE 토큰 50개 생성
        for (int i = 1; i <= 48; i++) {
            concertReservationFacade.createToken((long) i);
        }

        // 51번째 토큰 요청 -> WAIT 상태 예상
        TokenResponse response = concertReservationFacade.createToken(51L);
        TokenResponse response2 = concertReservationFacade.createToken(52L);
        TokenResponse response3 = concertReservationFacade.createToken(53L);

        assertEquals(response.getStatus(), TokenStatus.ACTIVE);
        assertEquals(response2.getStatus(), TokenStatus.ACTIVE);
        assertEquals(response3.getStatus(), TokenStatus.WAIT);
        assertEquals(response.getPosition(), 1);
        System.out.println("실제 토큰 상태값: "+ response3.getStatus());
        System.out.println("실제 순번: " + response3.getPosition());
    }

    @Test
    @DisplayName("현재 테이블에 ACTIVE 토큰은 32개이며, 100명이 토큰 생성 요청시 18명은 ACTIVE인 82명은 WAIT 토큰 반환")
    void test5() {
        // 사전 데이터: ACTIVE 토큰 32개 생성
        for (int i = 1; i <= 32; i++) {
            concertReservationFacade.createToken((long) i);
        }

        List<TokenResponse> responses = new ArrayList<>();

        // 100명의 사용자 요청
        for (int i = 33; i <= 132; i++) {
            responses.add(concertReservationFacade.createToken((long) i));
        }

        // ACTIVE 개수와 WAIT 개수 검증
        long activeCount = responses.stream().filter(r -> r.getStatus() == TokenStatus.ACTIVE).count();
        long waitCount = responses.stream().filter(r -> r.getStatus() == TokenStatus.WAIT).count();

        assertEquals(18, activeCount);
        assertEquals(82, waitCount);

        // 첫 WAIT 토큰의 위치 검증
        TokenResponse firstWaitResponse = responses.stream()
                .filter(r -> r.getStatus() == TokenStatus.WAIT)
                .findFirst()
                .orElseThrow();

        assertEquals(1, firstWaitResponse.getPosition());

        System.out.println("실제 토큰 상태값: "+ firstWaitResponse.getStatus());
        System.out.println("실제 순번: " + firstWaitResponse.getPosition());
    }
}
