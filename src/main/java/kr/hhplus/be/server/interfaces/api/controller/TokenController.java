package kr.hhplus.be.server.interfaces.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.usecase.ConcertReservationFacade;
import kr.hhplus.be.server.interfaces.api.dto.TokenResponse;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/token")
@Tag(name = "Token API", description = "토큰 관련 API")
public class TokenController {

    private final ConcertReservationFacade reservationFacade;
    private final RedissonClient redissonClient;

    private static final String TOKEN_PREFIX = "token:";
    private static final String WAITING_QUEUE_KEY = "token-waiting-queue";
    private static final String ACTIVE_QUEUE_KEY = "token-active-queue";

    public TokenController(ConcertReservationFacade reservationFacade, RedissonClient redissonClient) {
        this.reservationFacade = reservationFacade;
        this.redissonClient = redissonClient;
    }



    @GetMapping("/create")
    @Operation(summary = "대기열 토큰 생성", description = "좌석 예약으로 진입하기 위한 대기열 토큰을 생성/재발급 합니다.")
    public ResponseEntity<TokenResponse> createToken(@RequestParam("userId") Integer userId) {
        TokenResponse response = reservationFacade.createToken(userId);
        // x-token 헤더에 UUID 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-token", response.getTokenUuid());

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @DeleteMapping("/reset")
    public ResponseEntity<Void> resetTokens() {
        RKeys keys = redissonClient.getKeys();

        // 모든 토큰 삭제
        keys.deleteByPattern(TOKEN_PREFIX + "*");

        // 대기열 초기화
        redissonClient.getScoredSortedSet(WAITING_QUEUE_KEY).clear();
        redissonClient.getScoredSortedSet(ACTIVE_QUEUE_KEY).clear();

        return ResponseEntity.noContent().build();
    }

}