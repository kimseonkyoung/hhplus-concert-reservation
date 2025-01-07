package kr.hhplus.be.server.interfaces.api.controller;

import kr.hhplus.be.server.application.facade.ReservationFacade;
import kr.hhplus.be.server.domain.token.Token;
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
public class TokenController {

    private final ReservationFacade reservationFacade;

    public TokenController(ReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    @GetMapping("/create")
    public ResponseEntity<TokenResponse> createToken(@RequestParam("userId") Integer userId) {
        TokenResponse response = reservationFacade.createToken(userId);
        // x-token 헤더에 UUID 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-token", response.getTokenUuid());

        return new ResponseEntity(response, headers, HttpStatus.OK);
    }
}