package kr.hhplus.be.server.infrastructure.orm;

import kr.hhplus.be.server.domain.concert.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaSeatRepository extends JpaConcertRepository<Seat, Long> {

    @Query("SELECT s FROM Seat s WHERE s.concertScheduleId = :value")
    List<Seat> getSeatsForDate(@Param("value") Long concertScheduleId);
}
