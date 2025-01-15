package kr.hhplus.be.server.intergration;

import kr.hhplus.be.server.application.usecase.ConcertReservationFacade;
import kr.hhplus.be.server.domain.concert.Seat;
import kr.hhplus.be.server.domain.concert.SeatStatus;
import kr.hhplus.be.server.domain.concert.repository.SeatRepository;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.domain.reservation.service.PaymentService;
import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import kr.hhplus.be.server.infrastructure.orm.Token.JpaTokenRepository;
import kr.hhplus.be.server.interfaces.api.dto.ReservationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ConcertReservationIntegrationTest  {

    @Autowired
    private ConcertReservationFacade concertReservationFacade;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JpaTokenRepository jpaTokenRepository;

    @Autowired
    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        String tokenUuid = UUID.randomUUID().toString();
        String tokenUuid2 =  UUID.randomUUID().toString();
        // 테스트 데이터 초기화
        tokenRepository.save(Token.createWait(tokenUuid, LocalDateTime.now()));
        tokenRepository.save(Token.createWait(tokenUuid2, LocalDateTime.now()));
    }

    @AfterEach
    void cleanUp() {
        jpaTokenRepository.deleteAll();
    }

    @Test
    @DisplayName("")
    void test1() {
        String tokenUuid = UUID.randomUUID().toString();
        ReservationRequest request = new ReservationRequest(1L, 1L, 1L, tokenUuid);

        concertReservationFacade.reserveSeat(request, tokenUuid);
        // 좌석 상태 확인
        Seat seat = seatRepository.getSeatInfo(1L);
        assertEquals(SeatStatus.PROGRESS, seat.getStatus());

        // Token 상태 확인
        Token token = tokenRepository.findById(1L).orElseThrow();
        assertEquals(TokenStatus.ACTIVE, token.getStatus());

    }










}
