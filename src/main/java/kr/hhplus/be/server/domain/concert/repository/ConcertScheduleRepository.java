package kr.hhplus.be.server.domain.concert.repository;

import kr.hhplus.be.server.domain.concert.ConcertSchedule;

import java.util.List;

public interface ConcertScheduleRepository {

    List<ConcertSchedule> findAllDates(Long concertId, int offset, int limit);

    int getDatesTotalCount();
}
