package kr.hhplus.be.server.common.scheduler;

import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;


@RequiredArgsConstructor
public class TokenDeleteScheduler {

    private final TokenRepository tokenRepository;

    @Scheduled(cron = "0 0 12 * * ?") // 매일 새벽 12시에 실행
    public void cleanUpExpiredTokens() {
        tokenRepository.deleteByStatus(TokenStatus.EXPIRED);
    }
}