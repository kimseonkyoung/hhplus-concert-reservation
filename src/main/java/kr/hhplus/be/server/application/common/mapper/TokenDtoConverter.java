package kr.hhplus.be.server.application.common.mapper;

import kr.hhplus.be.server.domain.common.dto.TokenServiceResponse;
import kr.hhplus.be.server.interfaces.api.dto.TokenResponse;
import org.springframework.stereotype.Component;

@Component
public class TokenDtoConverter {
    public static TokenResponse toControllerResponse(TokenServiceResponse response) {
        return new TokenResponse(
                response.getTokenUuid(),
                response.getPosition(),
                response.getStatus(),
                response.getExpiredAt()
        );
    }
}
