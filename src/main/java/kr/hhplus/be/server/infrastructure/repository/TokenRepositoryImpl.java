package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenStatus;
import kr.hhplus.be.server.domain.token.repository.TokenRepository;
import kr.hhplus.be.server.infrastructure.orm.JpaTokenRespository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    private final JpaTokenRespository jpaTokenRespository;

    public TokenRepositoryImpl(JpaTokenRespository jpaTokenRespository) {
        this.jpaTokenRespository = jpaTokenRespository;
    }

    @Override
    public void save(Token token) {
        jpaTokenRespository.save(token);
    }

    @Override
    public Optional<Token> findById(long userId) {
        return jpaTokenRespository.findById(userId);
    }

    @Override
    public void updateTokenStatus(String tokenUuid, TokenStatus stats) {
        jpaTokenRespository.updateTokenStatus(tokenUuid, stats);
    }

    @Override
    public void deleteByStatus(TokenStatus status) {
        jpaTokenRespository.deleteByStatus(status);
    }

    @Override
    public int selectTokenPosition(Long tokenId) {
        return jpaTokenRespository.selectTokenPosition(tokenId);
    }

    @Override
    public Optional<Token> getToken(String tokenUuid) {
        return jpaTokenRespository.getToken(tokenUuid);
    }
}

