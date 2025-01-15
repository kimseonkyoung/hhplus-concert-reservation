package kr.hhplus.be.server.application.mapper.facade;

import kr.hhplus.be.server.domain.service.dto.TokenServiceResponse;
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
