package kr.hhplus.be.server.infrastructure.orm.Concert;

import kr.hhplus.be.server.domain.concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaConcertRepository<S, L extends Number> extends JpaRepository<Concert, Long> {

    @Query(value = "SELECT * FROM Concert c LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Concert> findAll(@Param("offset") int offset, @Param("limit") int limit);

    @Query("SELECT COUNT(c) FROM Concert c")
    int getServiceTotalCount();
}
