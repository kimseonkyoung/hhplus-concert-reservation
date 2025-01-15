package kr.hhplus.be.server.domain.concert.repository;

import kr.hhplus.be.server.domain.concert.Seat;

import java.util.List;

public interface SeatRepository {
    List<Seat> getSeatsForDate(Long concertScheduleId);
}
