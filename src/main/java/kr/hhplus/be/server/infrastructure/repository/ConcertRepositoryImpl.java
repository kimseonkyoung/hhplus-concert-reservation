package kr.hhplus.be.server.infrastructure.repository;

import kr.hhplus.be.server.domain.concert.Concert;
import kr.hhplus.be.server.domain.concert.repository.ConcertRepository;
import kr.hhplus.be.server.infrastructure.orm.JpaConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final JpaConcertRepository jpaConcertRepository;

    @Override
    public List <Concert> findAll(int offset, int limit) {
        return jpaConcertRepository.findAll(offset, limit);
    }

    @Override
    public int getServiceTotalCount() {
        return jpaConcertRepository.getServiceTotalCount();
    }

}
