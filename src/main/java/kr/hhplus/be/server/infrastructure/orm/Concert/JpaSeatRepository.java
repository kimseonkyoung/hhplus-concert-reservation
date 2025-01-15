package kr.hhplus.be.server.infrastructure.orm.Concert;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.concert.Seat;
import kr.hhplus.be.server.infrastructure.orm.Concert.JpaConcertRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaSeatRepository extends JpaConcertRepository<Seat, Long> {

    @Query("SELECT s FROM Seat s WHERE s.concertScheduleId = :value")
    List<Seat> getSeatsForDate(@Param("value") Long concertScheduleId);

    @Query("SELECT s FROM Seat s WHERE s.seatId = :value")
    Seat getSeatInfo(@Param("value") Long seatId);
}
