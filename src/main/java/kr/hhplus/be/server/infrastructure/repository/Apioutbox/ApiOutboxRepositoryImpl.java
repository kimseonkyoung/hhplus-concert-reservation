package kr.hhplus.be.server.infrastructure.repository.Apioutbox;

import kr.hhplus.be.server.common.log.AllRequiredLogger;
import kr.hhplus.be.server.domain.apioutbox.ApiOutbox;
import kr.hhplus.be.server.domain.apioutbox.OutboxStatus;
import kr.hhplus.be.server.domain.apioutbox.repository.ApiOutboxRepository;
import kr.hhplus.be.server.infrastructure.orm.Apioutbox.JpaApiOutboxRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllRequiredLogger
public class ApiOutboxRepositoryImpl implements ApiOutboxRepository {

    private final JpaApiOutboxRepository jpaApiOutboxRepository;

    public ApiOutboxRepositoryImpl(JpaApiOutboxRepository jpaApiOutboxRepository) {
        this.jpaApiOutboxRepository = jpaApiOutboxRepository;
    }

    @Override
    public Collection<Object> findByCreatedAtBefore(LocalDateTime now, OutboxStatus outboxStatus) {
        return jpaApiOutboxRepository.findByCreatedAtBefore(now, outboxStatus);
    }

    @Override
    public Optional<ApiOutbox> findById(Long id) {
        return jpaApiOutboxRepository.findById(id);
    }

    @Override
    public void save(ApiOutbox apiOutbox) {
        jpaApiOutboxRepository.save(apiOutbox);
    }

    @Override
    public List<ApiOutbox> findByStatus(OutboxStatus outboxStatus) {
        return jpaApiOutboxRepository.findByStatus(outboxStatus);
    }
}
