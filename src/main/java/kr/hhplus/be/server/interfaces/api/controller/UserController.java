package kr.hhplus.be.server.interfaces.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.usecase.ConcertReservationFacade;
import kr.hhplus.be.server.interfaces.api.dto.BalanceRequest;
import kr.hhplus.be.server.interfaces.api.dto.BalanceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "유저 관련 API")
public class UserController {

    private final ConcertReservationFacade reservationFacade;

    public UserController(ConcertReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    @GetMapping("/balance/{userId}")
    @Operation(summary = "유저 잔액 조회", description = "유저 ID를 통해 유저의 결제 잔액을 조회합니다.")
    public ResponseEntity<BalanceResponse> getUserBalance(@PathVariable("userId") long userId) {
        BalanceResponse response = reservationFacade.getUserBalance(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/balance/charge")
    @Operation(summary = "유저 잔액 충전", description = "유저 ID와 충전액을 통해 유저의 결제 잔액을 충전합니다.")
    public ResponseEntity<BalanceResponse> chargeUserBalance(@RequestBody BalanceRequest request) {
        BalanceResponse response = reservationFacade.chargeUserBalance(request);
        return ResponseEntity.ok(response);
    }
}