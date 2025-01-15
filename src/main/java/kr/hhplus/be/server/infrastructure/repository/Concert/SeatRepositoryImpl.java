package kr.hhplus.be.server.infrastructure.repository.Concert;

import kr.hhplus.be.server.common.log.AllRequiredLogger;
import kr.hhplus.be.server.domain.concert.Seat;
import kr.hhplus.be.server.domain.concert.repository.SeatRepository;
import kr.hhplus.be.server.infrastructure.orm.Concert.JpaSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@AllRequiredLogger
public class SeatRepositoryImpl implements SeatRepository {

    private final JpaSeatRepository seatRepository;

    @Override
    public List<Seat> getSeatsForDate(Long concertScheduleId) {
        return seatRepository. getSeatsForDate(concertScheduleId);
    }

    @Override
    public Seat getSeatInfo(Long seatId) {
        return seatRepository.getSeatInfo(seatId);
    }
}
