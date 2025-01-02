package kr.hhplus.be.server.interfaces.api.controller;

import kr.hhplus.be.server.interfaces.api.dto.ReservationRequest;
import kr.hhplus.be.server.interfaces.api.dto.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @PostMapping("/seat")
    public ResponseEntity<ReservationResponse> reserveSeat(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(
                new ReservationResponse(1L,1L, "SUCCESS")
        );
    }

}
