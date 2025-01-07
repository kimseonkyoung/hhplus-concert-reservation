package kr.hhplus.be.server.domain.token;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TokenGenerator {
    private static final long TOKEN_VALID_TIME = 5; // 분 단위

    public Token createToken() {
        String tokenUuid = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMinutes(TOKEN_VALID_TIME);
        TokenStatus status = TokenStatus.WAIT;
        return Token.create(tokenUuid, status, createdAt,expiredAt);
    }
}
