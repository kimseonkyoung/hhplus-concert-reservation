package kr.hhplus.be.server.interfaces.api.controller;

import kr.hhplus.be.server.application.facade.ReservationFacade;
import kr.hhplus.be.server.interfaces.api.dto.BalanceRequest;
import kr.hhplus.be.server.interfaces.api.dto.BalanceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ReservationFacade reservationFacade;

    public UserController(ReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<BalanceResponse> getUserBalance(@PathVariable("userId") long userId) {
        BalanceResponse response = reservationFacade.getUserBalance(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/balance/charge")
    public ResponseEntity<BalanceResponse> chargeUserBalance(@RequestBody BalanceRequest request) {
        BalanceResponse response = reservationFacade.chargeUserBalance(request);
        return ResponseEntity.ok(response);
    }
}