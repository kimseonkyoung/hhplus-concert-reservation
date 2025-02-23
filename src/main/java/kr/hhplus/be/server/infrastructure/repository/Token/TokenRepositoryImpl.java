package kr.hhplus.be.server.infrastructure.repository.Token;

import kr.hhplus.be.server.common.log.AllRequiredLogger;
import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllRequiredLogger
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    @Override
    public void save(Token token) {

    }

    @Override
    public Optional<Token> findById(String tokenUuid) {
        return Optional.empty();
    }

    @Override
    public Optional<Token> findById(long userId) {
        return Optional.empty();
    }

    @Override
    public void updateTokenStatus(String tokenUuid, TokenStatus status) {

    }

    @Override
    public void deleteByUserId(long userId, TokenStatus tokenStatus) {

    }

    @Override
    public List<String> findWaitingTokens(int batchSize) {
        return List.of();
    }

    @Override
    public void moveTokenToActiveQueue(String tokenUuid) {

    }

    @Override
    public int countActiveTokens() {
        return 0;
    }


    }


