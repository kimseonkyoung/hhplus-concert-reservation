package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.concert.ConcertSchedule;
import kr.hhplus.be.server.domain.concert.repository.ConcertScheduleRepository;
import kr.hhplus.be.server.infrastructure.orm.JpaConcertScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {

    private final JpaConcertScheduleRepository concertScheduleRepository;

    @Override
    public List<ConcertSchedule> findAllDates(Long concertId, int offset, int limit) {
        return concertScheduleRepository.findAllDates(concertId, offset, limit);
    }

    @Override
    public int getDatesTotalCount() {
        return concertScheduleRepository.getDatesTotalCount();
    }
}
