package kr.hhplus.be.server.interfaces.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.api.dto.ReservationRequest;
import kr.hhplus.be.server.interfaces.api.dto.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
@Tag(name = "Reservation API", description = "예약 결제 관련 API")
public class ReservationController {

    @PostMapping("/Seats")
    @Operation(summary = "콘서트 예약", description = "콘서트 스케줄 ID와 좌석 ID를 통해 좌석을 예약합니다.")
    public ResponseEntity<ReservationResponse> reserveSeat(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(
                new ReservationResponse(1L,1L, "SUCCESS")
        );
    }

}
