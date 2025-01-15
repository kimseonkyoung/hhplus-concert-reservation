package kr.hhplus.be.server.interfaces.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.usecase.ConcertReservationFacade;
import kr.hhplus.be.server.common.log.AllRequiredLogger;
import kr.hhplus.be.server.interfaces.api.dto.TokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@AllRequiredLogger
@Tag(name = "Token API", description = "토큰 관련 API")
public class TokenController {

    private final ConcertReservationFacade reservationFacade;

    public TokenController(ConcertReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    @GetMapping("/create")
    @Operation(summary = "대기열 토큰 생성", description = "좌석 예약으로 진입하기 위한 대기열 토큰을 생성/재발급 합니다.")
    public ResponseEntity<TokenResponse> createToken(@RequestParam("userId") Integer userId) {
        TokenResponse response = reservationFacade.createToken(userId);
        // x-token 헤더에 UUID 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-token", response.getTokenUuid());

        return new ResponseEntity(response, headers, HttpStatus.OK);
    }
}