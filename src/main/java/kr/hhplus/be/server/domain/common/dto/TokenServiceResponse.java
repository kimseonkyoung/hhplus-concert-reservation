package kr.hhplus.be.server.domain.common.dto;

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

    public TokenServiceResponse(String tokenUuid, int position, TokenStatus status) {
        this.tokenUuid = tokenUuid;
        this.position = position;
        this.status = status;
    }

    // 생성자를 팩토리 메서드로 생성
    public static TokenServiceResponse create(String tokenUuid, Integer position, TokenStatus status, LocalDateTime createdAt, LocalDateTime expiredAt) {
        return new TokenServiceResponse(tokenUuid, position, status, createdAt, expiredAt);
    }
    // polling 요청시 토큰 순번, 상태 반환 생성자 팩토리 메서드
    public static TokenServiceResponse createTokenPositionWithStatus(String tokenUuid, Integer position, TokenStatus status) {
        return new TokenServiceResponse(tokenUuid, position, status);
    }

}

