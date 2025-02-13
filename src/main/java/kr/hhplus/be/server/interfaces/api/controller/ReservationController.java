package kr.hhplus.be.server.interfaces.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import kr.hhplus.be.server.application.usecase.ConcertReservationFacade;
import kr.hhplus.be.server.common.log.AllRequiredLogger;
import kr.hhplus.be.server.interfaces.api.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservation")
@AllRequiredLogger
@Tag(name = "Reservation API", description = "예약 결제 관련 API")
public class ReservationController {

    private final ConcertReservationFacade facade;

    public ReservationController(ConcertReservationFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/Seats")
    @Operation(summary = "콘서트 예약", description = "콘서트 스케줄 ID와 좌석 ID를 통해 좌석을 예약합니다.")
    public ResponseEntity<ReservationResponse> reserveSeat(
            @RequestBody ReservationRequest requestBody,
            HttpServletRequest request
    ) {
        String tokenUuid = request.getHeader("x-token");
        ReservationResponse response = facade.reserveProgressSeat(requestBody, tokenUuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment")
    @Operation(summary = "콘서트 좌석 결제", description = "예약 ID를 통해 해당 좌석을 결제합니다.")
    public ResponseEntity<PaymentResponse> paymentSeat(
            @RequestParam(name = "reservationId") Long reservationId,
            HttpServletRequest request
    ) {
        String tokenUuid = request.getHeader("x-token");
        PaymentResponse response = facade.reserveAndPaymentCompleted(reservationId, tokenUuid);
        return ResponseEntity.ok(response);
    }



}
