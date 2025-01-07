package kr.hhplus.be.server.interfaces.api.dto;

import kr.hhplus.be.server.domain.token.TokenStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String tokenUuid;
    private Integer position;
    private TokenStatus status;
    private LocalDateTime expiredAt;

    public TokenResponse(String tokenUuid, Integer position, TokenStatus status) {
        this.tokenUuid = tokenUuid;
        this.position = position;
        this.status = status;
    }
}
