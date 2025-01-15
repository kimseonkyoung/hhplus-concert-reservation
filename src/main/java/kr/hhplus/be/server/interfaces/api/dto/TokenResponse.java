package kr.hhplus.be.server.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.domain.token.TokenStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "대기열 토큰 응답 데이터")
public class TokenResponse {

    @Schema(description = "토큰 UUID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String tokenUuid;

    @Schema(description = "대기열 순번", example = "14")
    private Integer position;

    @Schema(description = "토큰 상태", example = "WAIT")
    private TokenStatus status;

    @Schema(description = "토큰 만료시간", example = "2025-01-11 12:33:05")
    private LocalDateTime expiredAt;

    public TokenResponse(String tokenUuid, Integer position, TokenStatus status) {
        this.tokenUuid = tokenUuid;
        this.position = position;
        this.status = status;
    }
}
