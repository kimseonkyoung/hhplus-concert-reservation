//package kr.hhplus.be.server.infrastructure.orm.Token;
//
//import kr.hhplus.be.server.domain.token.Token;
//import kr.hhplus.be.server.domain.token.TokenStatus;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//public interface JpaTokenRepository extends JpaRepository<Token, Long> {
//
//    @Modifying
//    @Query("UPDATE Token t SET t.status = :status WHERE t.tokenUuid = :tokenUuid")
//    void updateTokenStatus(@Param("tokenUuid") String tokenUuid, @Param("status") TokenStatus status);
//
//    @Query("DELETE FROM Token t WHERE t.status = :status")
//    void deleteByStatus(@Param("status") TokenStatus status);
//
//    @Query("SELECT COUNT(t) + 1 FROM Token t WHERE t.tokenId < :value AND t.status = 'WAIT'")
//    int selectTokenPosition(@Param("value") Long tokenId);
//
//    @Query("SELECT t FROM Token t WHERE t.tokenUuid < :value")
//    Optional<Token> getToken(@Param("value") String tokenUuid);
//
//    @Modifying
//    @Query("UPDATE Token t SET t.expiredAt = :expiredAt WHERE t.tokenUuid = :uuid")
//    void setExpiredTimeToken(@Param("uuid") String uuid, @Param("expiredAt") LocalDateTime expiredAt);
//
//    @Modifying
//    @Query("UPDATE Token t SET t.status =: status WHERE t.tokenUuid = :uuid")
//    void expireTokenOnCompleted(@Param("uuid") String tokenUuid, @Param("status") TokenStatus status);
//
//    @Query("SELECT t FROM Token t WHERE t.status =: status AND t.expiredAt <= : now")
//    List<Token> findExpiredActiveTokens(@Param("now") LocalDateTime now, @Param("status") TokenStatus tokenStatus);
//
//    @Modifying
//    @Query("update Token t SET t.status =: status WHERE t.tokenId IN : tokenIds")
//    void updateTokenExpired(@Param("tokenIds") List<Long> expiredActiveIds, @Param("status") TokenStatus tokenStatus);
//
//    @Query(value = "SELECT t FROM Token t WHERE t.status =: status ORDER BY t.tokenId ASC LIMIT :limit", nativeQuery = true)
//    List<Token> findWaitTokens(@Param("status") TokenStatus tokenStatus, @Param("limit") int expiredActiveCount);
//
//    @Modifying
//    @Query("update Token t SET t.status =: status WHERE t.tokenId IN : tokenIds")
//    void updateTokenActive(@Param("Ids") List<Long> waitActiveIds, @Param("stats") TokenStatus status);
//
//    @Query(value = "SELECT count(t) FROM Token t WHERE t.status = :status")
//    int countActiveToken(@Param("status") TokenStatus tokenStatus);
//}
