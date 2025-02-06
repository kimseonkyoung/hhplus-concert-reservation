package kr.hhplus.be.server.domain.token.repository;

import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    void save(Token token);
    
    Optional<Token> findById(String tokenUuid);

    void updateTokenStatus(String tokenUuid, TokenStatus status);
    
    void deleteByUserId(long userId, TokenStatus tokenStatus);

    List<Token> findExpiredActiveTokens(LocalDateTime now, TokenStatus tokenStatus);
}
