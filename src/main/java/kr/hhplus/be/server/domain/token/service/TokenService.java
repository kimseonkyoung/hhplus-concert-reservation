package kr.hhplus.be.server.domain.token.service;

import kr.hhplus.be.server.common.globalErrorHandler.ErrorCode;
import kr.hhplus.be.server.common.log.AllRequiredLogger;
import kr.hhplus.be.server.domain.common.mapper.TokenEntityConverter;
import kr.hhplus.be.server.domain.common.dto.TokenServiceResponse;
import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenGenerator;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllRequiredLogger
public class TokenService {

    private final TokenRepository tokenRepository;
    private final TokenGenerator tokenGenerator;

    public TokenService(TokenRepository tokenRepository, TokenGenerator tokenGenerator) {
        this.tokenRepository = tokenRepository;
        this.tokenGenerator = tokenGenerator;
    }

    //대량의 요청이 들어온다면 동시성 제어를 해야할 수도 있음.
    public TokenServiceResponse createToken(long userId) {
        // 0. DB에서 해당 userId로 Token 존재유무 확인
        Optional<Token> oldToken = tokenRepository.findById(userId);
        // 1. 기존 토큰 처리 (만료 상태로 변경)
        oldToken.ifPresent(token -> tokenRepository.updateTokenStatus(token.getTokenUuid(), TokenStatus.EXPIRED));
        // 2. 현재 active인 토큰 갯수 조회
        int activeCount = tokenRepository.countActiveToken(TokenStatus.ACTIVE);
        // 3. 토큰 제조
        Token newToken = tokenGenerator.createToken(activeCount);
        // 4. 토큰 저장
        tokenRepository.save(newToken);
        // 5. 토큰 대기열 반환
        int position = tokenRepository.selectTokenPosition(newToken.getTokenId());
        // 6. 반환할 토큰 responseDto로 셋팅
        TokenServiceResponse response = TokenEntityConverter.toServiceResponse(newToken, position);
        return response;
    }

    /** 토큰 대기열 순번 조회 */
    public TokenServiceResponse getTokenStatusAndPosition(String tokenUuid) {
        Token token = tokenRepository.getToken(tokenUuid)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.TOKEN_NOT_FOUND + tokenUuid));
        int position = tokenRepository.selectTokenPosition(token.getTokenId());
        TokenServiceResponse response = TokenEntityConverter.toServiceResponse(token, position);
        return response;
    }

    /** 토큰 좌석 예약 진입시 만료시간 생성 */
    public void setExpiredTimeToken(String uuid, LocalDateTime expiredAt) {
        tokenRepository.setExpiredTimeToken(uuid, expiredAt);
    }

    /** 결제 완료시 토큰 상태 만료로 변경 */
    public void expireTokenOnCompleted(String tokenUuid) {
        tokenRepository.expireTokenOnCompleted(tokenUuid, TokenStatus.EXPIRED);
    }

    /** 만료 시간이 지난 토큰을 expired 시키고, 그만큼의 토큰을 WAIT -> ACTIVE로 변경 */
    @Transactional
    public void processExpiredActiveTokens() {
        // 1. 만료된 토큰 조회
        LocalDateTime now = LocalDateTime.now();
        List<Token> expiredActiveTokens = tokenRepository.findExpiredActiveTokens(now, TokenStatus.ACTIVE);

        // 2. 만료된 ACTIVE 토큰 수 확인
        int expiredActiveCount = expiredActiveTokens.size();
        if (expiredActiveCount > 0 ) {
            // 2-1. ACTIVE 토큰을 EXPIRED 업데이트
            List<Long> expiredActiveIds = expiredActiveTokens.stream().map(Token::getTokenId).toList();
            tokenRepository.updateTokenExpired(expiredActiveIds, TokenStatus.EXPIRED);
            /** log */
        } else {
            /** log */
        }

        // 3. 만료된 토큰 수만큼 WAIT 토큰 조회
        if (expiredActiveCount > 0 ) {
            List<Token> waitTokens = tokenRepository.findWaitTokens(TokenStatus.WAIT, expiredActiveCount);

            if (!waitTokens.isEmpty()) {
                // 3-1. WAIT 토큰을 ACTIVE로 업데이트
                List<Long> waitTokensIds = waitTokens.stream().map(Token::getTokenId).toList();
                tokenRepository.updateTokenActive(waitTokensIds, TokenStatus.ACTIVE);
                /** log */
            } else {
                /** log */
            }
        }
    }
}
