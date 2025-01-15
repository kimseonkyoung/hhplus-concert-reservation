package kr.hhplus.be.server.domain.token.repository;

import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenStatus;

import java.util.Optional;

public interface TokenRepository {
    void save(Token token);

    Optional<Token> findById(long userId);

    void updateTokenStatus(String tokenUuid, TokenStatus status);

    void deleteByStatus(TokenStatus status);

    int selectTokenPosition(Long tokenId);

    Optional<Token> getToken(String tokenUuid);
}
