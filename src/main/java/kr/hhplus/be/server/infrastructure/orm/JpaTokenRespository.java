package kr.hhplus.be.server.infrastructure.orm;

import kr.hhplus.be.server.domain.token.Token;
import kr.hhplus.be.server.domain.token.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaTokenRespository extends JpaRepository<Token, Long> {

    @Query("UPDATE Token t SET t.status = :status WHERE t.tokenUuid = :tokenUuid")
    void updateTokenStatus(@Param("tokenUuid") String tokenUuid, @Param("status") TokenStatus status);


    @Query("DELETE FROM Token t WHERE t.status = :status")
    void deleteByStatus(@Param("status") TokenStatus status);

    @Query("SELECT COUNT(t) + 1 FROM Token t WHERE t.tokenId < :value AND t.status = 'WAIT'")
    int selectTokenPosition(@Param("value") Long tokenId);

    @Query("SELECT t FROM Token t WHERE t.tokenUuid < :value")
    Optional<Token> getToken(String tokenUuid);

}
