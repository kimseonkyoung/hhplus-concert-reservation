package kr.hhplus.be.server.domain.service.dto;

import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenServiceResponse {
    private String tokenUuid;
    private int remainingTime;
    private Integer position;
    private TokenStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;


    public TokenServiceResponse(String tokenUuid, Integer position, TokenStatus status, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.tokenUuid = tokenUuid;
        this.position = position;
        this.status = status;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }


    // 생성자를 팩토리 메서드로 생성
    public static TokenServiceResponse create(String tokenUuid, Integer position, TokenStatus status, LocalDateTime createdAt, LocalDateTime expiredAt) {
        return new TokenServiceResponse(tokenUuid, position, status, createdAt, expiredAt);
    }

}

