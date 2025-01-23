package kr.hhplus.be.server.integration;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import kr.hhplus.be.server.application.usecase.ConcertReservationFacade;
import kr.hhplus.be.server.domain.common.exception.SeatProgressException;
import kr.hhplus.be.server.domain.concert.Seat;
import kr.hhplus.be.server.domain.concert.SeatStatus;
import kr.hhplus.be.server.domain.concert.repository.SeatRepository;
import kr.hhplus.be.server.domain.reservation.Reservation;
import kr.hhplus.be.server.domain.reservation.service.ReservationService;
import kr.hhplus.be.server.infrastructure.orm.Reservation.JpaReservationRepository;
import kr.hhplus.be.server.interfaces.api.dto.ReservationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 예약 API 테스트:
 * 이미 해당 요청시 요청하는 토큰들은 ACTIVE 상태인 것을 전제로 테스트.
 * 1) 한 자리에 다수(3명 이상) 동시 요청시 -> 한 명 성공, 나머지 실패
 */
@SpringBootTest
@Transactional
public class ReservationIntegrationConcurrencyTest {

    @Autowired
    private ConcertReservationFacade concertReservationFacade;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private JpaReservationRepository reservationRepository;

    @Test
    @DisplayName("유저 100명 분산락 테스트")
    void test1 () {
        int numberOfUsers = 100; // 100명의 동시 요청
        CompletableFuture<Void> futures = new CompletableFuture<>();
        ExecutorService executorService = Executors.newFixedThreadPool(30);

        // CompletableFuture 리스트 생성
        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfUsers; i++) {
            Long userId = i + 1L; // 각 사용자 ID
            String tokenUuid = UUID.randomUUID().toString();
            ReservationRequest reservationRequest = new ReservationRequest(1L, 1L, userId);

            // 비동기 요청 추가
            futures = CompletableFuture.runAsync(() -> {
                try {
                    concertReservationFacade.reserveSeat(reservationRequest, tokenUuid);
                } catch (SeatProgressException e) {
                    System.out.println("예약 실패: " + e.getMessage());
                }
            }, executorService);
            System.out.println("요청 횟수: " + (i+1));
        }

        CompletableFuture.allOf(futures).join();
        long end = System.currentTimeMillis();

        // 예약 결과 검증
        Seat reservedSeat = seatRepository.getSeatInfo(1L);
        assertEquals(SeatStatus.PROGRESS, reservedSeat.getStatus());
        assertNotNull(reservedSeat.getSeatId());
        List<Reservation> reservations = reservationRepository.findAllBySeatId(reservedSeat.getSeatId());
        assertEquals(reservations.size(), 1);
        System.out.println("총 걸린 시간: " + (end - start) + "ms");
        System.out.println("예약 정보 확인: " + reservationService.findById(reservedSeat.getSeatId()));
    }

    @Test
    void testSingleSeatReservationWithPessimisticLocking() {
        int numberOfUsers = 100; // 100명의 동시 요청
        CompletableFuture<Void> futures = new CompletableFuture<>();
        ExecutorService executorService = Executors.newFixedThreadPool(30);

        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfUsers; i++) {
            long userId = i + 1L; // 각 사용자 ID
            String tokenUuid = UUID.randomUUID().toString();
            ReservationRequest reservationRequest = new ReservationRequest(1L, 1L, userId);

            // 비동기 요청 추가
            futures = CompletableFuture.runAsync(() -> {
                try {
                    concertReservationFacade.reserveSeat(reservationRequest, tokenUuid);
                } catch (Exception e) {
                    System.out.println("예약 실패: " + e.getMessage());
                }
            }, executorService);
            System.out.println("요청 횟수: " + (i+1));
        }

        CompletableFuture.allOf(futures).join();
        long end = System.currentTimeMillis();

        // 예약 결과 검증
        Seat reservedSeat = seatRepository.getSeatInfo(1L);
        assertEquals("RESERVED", reservedSeat.getStatus());
        assertNotNull(reservedSeat.getSeatId());
        System.out.println("총 걸린 시간: " + (end - start) + "ms");
        System.out.println("예약 s: " + reservedSeat.getConcertScheduleId());
    }


}
