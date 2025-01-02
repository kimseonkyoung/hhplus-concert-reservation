package kr.hhplus.be.server.interfaces.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.api.dto.BalanceChargeRequest;
import kr.hhplus.be.server.interfaces.api.dto.BalanceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getUserBalance(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(new BalanceResponse(1L, 10000));
    }

    @PostMapping("/balance/charge")
    public ResponseEntity<BalanceResponse> chargeUserBalance(@RequestBody BalanceChargeRequest request) {
        // 충전 금액이 5000일 경우
        return ResponseEntity.ok(new BalanceResponse(1L, 15000));
    }
}