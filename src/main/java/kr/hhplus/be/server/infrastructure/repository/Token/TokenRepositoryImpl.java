package kr.hhplus.be.server.infrastructure.repository.Token;

import kr.hhplus.be.server.common.log.AllRequiredLogger;
import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import kr.hhplus.be.server.infrastructure.orm.Token.JpaTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllRequiredLogger
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final JpaTokenRepository jpaTokenRepository;

    @Override
    public void save(Token token) {
        jpaTokenRepository.save(token);
    }

    @Override
    public Optional<Token> findById(long userId) {
        return jpaTokenRepository.findById(userId);
    }

    @Override
    public void updateTokenStatus(String tokenUuid, TokenStatus stats) {
        jpaTokenRepository.updateTokenStatus(tokenUuid, stats);
    }

    @Override
    public void deleteByStatus(TokenStatus status) {
        jpaTokenRepository.deleteByStatus(status);
    }

    @Override
    public int selectTokenPosition(Long tokenId) {
        return jpaTokenRepository.selectTokenPosition(tokenId);
    }

    @Override
    public Optional<Token> getToken(String tokenUuid) {
        return jpaTokenRepository.getToken(tokenUuid);
    }

    @Override
    public void setExpiredTimeToken(String uuid, LocalDateTime expiredAt) {
        jpaTokenRepository.setExpiredTimeToken(uuid, expiredAt);
    }

    @Override
    public void expireTokenOnCompleted(String tokenUuid, TokenStatus status) {
        jpaTokenRepository.expireTokenOnCompleted(tokenUuid, status);
    }

    @Override
    public List<Token> findExpiredActiveTokens(LocalDateTime now, TokenStatus tokenStatus) {
        return jpaTokenRepository.findExpiredActiveTokens(now, tokenStatus);
    }

    @Override
    public void updateTokenExpired(List<Long> expiredActiveIds, TokenStatus tokenStatus) {
        jpaTokenRepository.updateTokenExpired(expiredActiveIds, tokenStatus);
    }

    @Override
    public List<Token> findWaitTokens(TokenStatus tokenStatus, int expiredActiveCount) {
        return jpaTokenRepository.findWaitTokens(tokenStatus, expiredActiveCount);
    }

    @Override
    public void updateTokenActive(List<Long> waitTokensIds, TokenStatus tokenStatus) {
        jpaTokenRepository.updateTokenActive(waitTokensIds, TokenStatus.ACTIVE);
    }

    @Override
    public int countActiveToken(TokenStatus tokenStatus) {
        return jpaTokenRepository.countActiveToken(tokenStatus);
    }
}

