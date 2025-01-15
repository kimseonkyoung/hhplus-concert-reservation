package kr.hhplus.be.server.common.scheduler;

import kr.hhplus.be.server.domain.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class TokenExpiredAndActivationScheduler {

    private final TokenService tokenService;

    @Scheduled(cron = "*/10 * * * * ?") // 매 10초마다 실행
    public void processExpiredTokensAndReactivate() {
        tokenService.processExpiredActiveTokens();
    }
}



