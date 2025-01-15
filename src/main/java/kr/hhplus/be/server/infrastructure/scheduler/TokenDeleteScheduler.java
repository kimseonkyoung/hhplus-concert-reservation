package kr.hhplus.be.server.infrastructure.scheduler;

import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import org.springframework.scheduling.annotation.Scheduled;



public class TokenDeleteScheduler {

    private final TokenRepository tokenRepository;

    public TokenDeleteScheduler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(cron = "0 0 12 * * ?") // 매일 새벽 12시에 실행
    public void cleanUpExpiredTokens() {
        tokenRepository.deleteByStatus(TokenStatus.EXPIRED);
    }
}