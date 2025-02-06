package kr.hhplus.be.server.domain.token.service;

import kr.hhplus.be.server.domain.common.mapper.TokenEntityConverter;
import kr.hhplus.be.server.domain.common.dto.TokenServiceResponse;
import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenGenerator;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
@Slf4j
public class TokenService {

    private final TokenRepository tokenRepository;
    private final TokenGenerator tokenGenerator;
    private final RedissonClient redissonClient;

    private static final String TOKEN_PREFIX = "token:";
    private static final String WAITING_QUEUE_KEY = "token-waiting-queue";
    private static final String ACTIVE_QUEUE_KEY = "token-active-queue";
    private static final int TTL_SECONDS = 300;


    Logger logger = Logger.getLogger(TokenService.class.getName());

    public TokenService(TokenRepository tokenRepository, TokenGenerator tokenGenerator, RedissonClient redissonClient) {
        this.tokenRepository = tokenRepository;
        this.tokenGenerator = tokenGenerator;
        this.redissonClient = redissonClient;
    }

    public TokenServiceResponse createToken(long userId) {
        // 1. 기존 ACTIVE 토큰이 있으면 삭제
        tokenRepository.deleteByUserId(userId, TokenStatus.ACTIVE);

        // 2. 현재 Active 토큰 개수 조회
        int activeCount = countActiveTokens();

        // 3. 새로운 토큰 생성
        Token newToken = tokenGenerator.createToken(activeCount);

        // 4. 토큰 저장 (리포지토리 사용)
        tokenRepository.save(newToken);

        // 5. 대기열 추가
        if (newToken.getStatus() == TokenStatus.ACTIVE) {
            addToActiveQueue(newToken.getTokenUuid());
        } else {
            addToWaitingQueue(newToken.getTokenUuid());
        }

        logger.info("Stored token status: " + newToken.getStatus());

        // 6. 대기열 순번 조회
        int position = getTokenPositionWithStatus(newToken.getTokenUuid()).getPosition();

        // 7. 토큰 응답 생성
        return TokenEntityConverter.toServiceResponse(newToken, position);
    }

    public TokenServiceResponse getTokenPositionWithStatus(String tokenUuid) {
        RScoredSortedSet<String> waitingQueue = redissonClient.getScoredSortedSet(WAITING_QUEUE_KEY);
        Integer rank = waitingQueue.rank(tokenUuid);
        int position = (rank != null) ? rank + 1 : -1;

        // 리포지토리 활용하여 토큰 상태 조회
        Optional<Token> tokenOptional = tokenRepository.findById(tokenUuid);
        TokenStatus status = tokenOptional.map(Token::getStatus).orElse(TokenStatus.WAIT);

        return TokenServiceResponse.createTokenPositionWithStatus(tokenUuid, position, status);
    }

    private void addToWaitingQueue(String tokenUuid) {
        RScoredSortedSet<String> waitingQueue = redissonClient.getScoredSortedSet(WAITING_QUEUE_KEY);
        waitingQueue.add(System.currentTimeMillis(), tokenUuid);
    }

    private void addToActiveQueue(String tokenUuid) {
        RScoredSortedSet<String> activeQueue = redissonClient.getScoredSortedSet(ACTIVE_QUEUE_KEY);
        activeQueue.add(System.currentTimeMillis(), tokenUuid);
    }

    private int countActiveTokens() {
        RScoredSortedSet<String> activeQueue = redissonClient.getScoredSortedSet(ACTIVE_QUEUE_KEY);
        return activeQueue.size();
    }

    public void expireTokenOnCompleted(String tokenUuid) {
        tokenRepository.updateTokenStatus(tokenUuid, TokenStatus.EXPIRED);
    }

//    /** 만료 시간이 지난 토큰을 expired 시키고, 그만큼의 토큰을 WAIT -> ACTIVE로 변경 */
//    @Transactional
//    public void processExpiredActiveTokens() {
//        // 1. 만료된 토큰 조회
//        LocalDateTime now = LocalDateTime.now();
//        List<Token> expiredActiveTokens = tokenRepository.findExpiredActiveTokens(now, TokenStatus.ACTIVE);
//
//        // 2. 만료된 ACTIVE 토큰 수 확인
//        int expiredActiveCount = expiredActiveTokens.size();
//        if (expiredActiveCount > 0 ) {
//            // 2-1. ACTIVE 토큰을 EXPIRED 업데이트
//            List<Long> expiredActiveIds = expiredActiveTokens.stream().map(Token::getTokenId).toList();
//            tokenRepository.updateTokenExpired(expiredActiveIds, TokenStatus.EXPIRED);
//            /** log */
//        } else {
//            /** log */
//        }
//
//        // 3. 만료된 토큰 수만큼 WAIT 토큰 조회
//        if (expiredActiveCount > 0 ) {
//            List<Token> waitTokens = tokenRepository.findWaitTokens(TokenStatus.WAIT, expiredActiveCount);
//
//            if (!waitTokens.isEmpty()) {
//                // 3-1. WAIT 토큰을 ACTIVE로 업데이트
//                List<Long> waitTokensIds = waitTokens.stream().map(Token::getTokenId).toList();
//                tokenRepository.updateTokenActive(waitTokensIds, TokenStatus.ACTIVE);
//                /** log */
//            } else {
//                /** log */
//            }
//        }
//    }
//
//    public void setExpiredTimeToken(String tokenUuid, LocalDateTime expiredAt) {
//    }
}
