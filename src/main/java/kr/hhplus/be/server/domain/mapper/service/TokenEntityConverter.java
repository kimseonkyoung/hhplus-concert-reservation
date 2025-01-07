package kr.hhplus.be.server.domain.mapper.service;

import kr.hhplus.be.server.domain.service.dto.TokenServiceResponse;
import kr.hhplus.be.server.domain.token.Token;

public class TokenEntityConverter {
    public static TokenServiceResponse toServiceResponse(Token token, int position) {
        return TokenServiceResponse.create(
                token.getTokenUuid(),
                position,
                token.getStatus(),
                token.getCreatedAt(),
                token.getExpiredAt());
    }
}
