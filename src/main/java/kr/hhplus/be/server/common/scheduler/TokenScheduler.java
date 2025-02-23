package kr.hhplus.be.server.common.scheduler;

import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class TokenScheduler {

    private static final int BATCH_SIZE = 10;
    private static final int MIN_ACTIVE_TOKENS = 50;

    private final TokenRepository tokenRepository;

    public TokenScheduler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    // 놀이공원 방식 적용: 일정 주기로 WAITING -> ACTIVE 전환
    @Scheduled(fixedRate = 5000)
    @Transactional
    public void promoteWaitingToActive() {
        log.info("WAITING 토큰 {} 개를 ACTIVE 토큰으로 변환중 ...");
        List<String> waitingTokens = tokenRepository.findWaitingTokens(BATCH_SIZE);
        if (waitingTokens.isEmpty()) {
            log.info("WAITING 토큰이 없습니다. 대기중 ...");
            return;
        }

        for (String tokenUuid : waitingTokens) {
            tokenRepository.updateTokenStatus(tokenUuid, TokenStatus.ACTIVE);
            tokenRepository.moveTokenToActiveQueue(tokenUuid);
        }

        log.info("{}개의 WAITING 토큰이 ACTIVE로 변경되었습니다!", waitingTokens.size());
    }

    //스케줄러 방식: ACTIVE 토큰 개수 부족시 WAITING -> ACTIVE 추가 전환
    @Scheduled(fixedRate = 5000) // 5초마다 실행
    @Transactional
    public void checkAndFillActiveTokens() {
        int activeCount = tokenRepository.countActiveTokens();

        if (activeCount < MIN_ACTIVE_TOKENS) {
            int needMore = MIN_ACTIVE_TOKENS - activeCount;
            log.info("ACTIVE 토큰 부족 {}개 추가 활성화 필요.", needMore);

            List<String> waitingTokens = tokenRepository.findWaitingTokens(needMore);
            if (!waitingTokens.isEmpty()) {
                for (String tokenUuid : waitingTokens) {
                    tokenRepository.updateTokenStatus(tokenUuid, TokenStatus.ACTIVE);
                    tokenRepository.moveTokenToActiveQueue(tokenUuid);
                }
                log.info("{}개의 WAITING 토큰이 추가로 ACTIVE로 전환되었습니다!", waitingTokens.size());
            }
        }
    }
}