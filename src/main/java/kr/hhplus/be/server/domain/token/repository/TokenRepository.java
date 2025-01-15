package kr.hhplus.be.server.domain.token.repository;

import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository {
    void save(Token token);

    Optional<Token> findById(long userId);

    void updateTokenStatus(String tokenUuid, TokenStatus status);

    void deleteByStatus(TokenStatus status);

    int selectTokenPosition(Long tokenId);

    Optional<Token> getToken(String tokenUuid);

    void setExpiredTimeToken(String uuid, LocalDateTime expiredAt);

    void expireTokenOnCompleted(String tokenUuid, TokenStatus status);

    List<Token> findExpiredActiveTokens(LocalDateTime now, TokenStatus tokenStatus);

    void updateTokenExpired(List<Long> expiredActiveIds, TokenStatus tokenStatus);

    List<Token> findWaitTokens(TokenStatus tokenStatus, int expiredActiveCount);

    void updateTokenActive(List<Long> waitActiveIds, TokenStatus status);

    int countActiveToken(TokenStatus tokenStatus);
}
