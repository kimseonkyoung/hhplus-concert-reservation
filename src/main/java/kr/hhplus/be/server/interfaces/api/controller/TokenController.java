package kr.hhplus.be.server.interfaces.api.controller;

import kr.hhplus.be.server.interfaces.api.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queue")
public class TokenController {

    @GetMapping("/status")
    public ResponseEntity<TokenResponse> getQueueStatus(@RequestParam("uuid") String uuid) {
        //UUID 1번일 경우 진입, 101번일 경우 대기
        if ("1".equals(uuid)) {
            return ResponseEntity.ok(new TokenResponse(uuid, 0L, "ACTIVE")); // 활성화됨
        } else {
            return ResponseEntity.ok(new TokenResponse(uuid, 5L, "WAIT")); // 대기 중
        }
    }
}