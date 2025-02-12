package kr.hhplus.be.server.domain.common.mapper;

import kr.hhplus.be.server.domain.common.dto.TokenServiceResponse;
import kr.hhplus.be.server.domain.token.Token;

public class TokenEntityConverter {
    public static TokenServiceResponse toServiceResponse(Token token, int position) {
        return TokenServiceResponse.create(
                token.getTokenUuid(),
                position,
                token.getStatus(),
                token.getCreatedAt());
    }
}
