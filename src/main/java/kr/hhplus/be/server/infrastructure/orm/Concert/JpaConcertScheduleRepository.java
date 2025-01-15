package kr.hhplus.be.server.infrastructure.orm.Concert;

import kr.hhplus.be.server.domain.concert.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaConcertScheduleRepository extends JpaRepository<ConcertSchedule, Long> {
    @Query(value = "SELECT * FROM ConcertSchedule c WHERE c.concertId = :concertId LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<ConcertSchedule> findAllDates(@Param("concertId") Long concertId, @Param("offset") int offset, @Param("limit") int limit);

    @Query("SELECT COUNT(c) FROM ConcertSchedule c")
    int getDatesTotalCount();
}
