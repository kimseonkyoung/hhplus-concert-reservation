package kr.hhplus.be.server.domain.apioutbox.repository;

import kr.hhplus.be.server.domain.apioutbox.ApiOutbox;
import kr.hhplus.be.server.domain.apioutbox.OutboxStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ApiOutboxRepository {
    Collection<Object> findByCreatedAtBefore(LocalDateTime now, OutboxStatus outboxStatus);

    Optional<ApiOutbox> findById(Long id);
    void save(ApiOutbox apiOutbox);

    List<ApiOutbox> findByStatus(OutboxStatus outboxStatus);
}
