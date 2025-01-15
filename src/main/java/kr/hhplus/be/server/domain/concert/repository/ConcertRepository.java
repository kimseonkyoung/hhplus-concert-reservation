package kr.hhplus.be.server.domain.concert.repository;

import kr.hhplus.be.server.domain.concert.Concert;
import kr.hhplus.be.server.domain.concert.ConcertSchedule;

import java.util.List;

public interface ConcertRepository {
    List <Concert> findAll(int offset, int limit);

    int getServiceTotalCount();
}
