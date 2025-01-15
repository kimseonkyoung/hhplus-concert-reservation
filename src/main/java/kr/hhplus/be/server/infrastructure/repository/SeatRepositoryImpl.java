package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.concert.Seat;
import kr.hhplus.be.server.domain.concert.repository.SeatRepository;
import kr.hhplus.be.server.infrastructure.orm.JpaSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final JpaSeatRepository seatRepository;

    @Override
    public List<Seat> getSeatsForDate(Long concertScheduleId) {
        return seatRepository. getSeatsForDate(concertScheduleId);
    }
}
