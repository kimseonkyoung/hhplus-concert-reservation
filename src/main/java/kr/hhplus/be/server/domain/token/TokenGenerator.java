package kr.hhplus.be.server.domain.token;

import kr.hhplus.be.server.common.log.AllRequiredLogger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllRequiredLogger
public class TokenGenerator {
    private static final long MAX_ACTIVE_TOKENS = 50;

    public Token createToken(int activeCount) {
        String tokenUuid = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();

        // 1. 현재 ACTIVE인 토큰 갯수가 예약 가능한 수 보다 작음.
        if (activeCount < MAX_ACTIVE_TOKENS) {
            return createActiveToken(tokenUuid, createdAt);
        } else {
            // 2. 현재 ACTIVE인 토큰 갯수가 예약 가능한 수 보다 큼.
            return createWaitToken(tokenUuid, createdAt);
        }
    }

    private Token createActiveToken(String tokenUuid, LocalDateTime createdAt) {
        return Token.createActive(tokenUuid, createdAt);
    }

    private Token createWaitToken(String tokenUuid, LocalDateTime createdAt) {
        return Token.createWait(tokenUuid, createdAt);
    }
}
