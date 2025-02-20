package kr.hhplus.be.server.infrastructure.orm.Apioutbox;

import kr.hhplus.be.server.domain.apioutbox.ApiOutbox;
import kr.hhplus.be.server.domain.apioutbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface JpaApiOutboxRepository extends JpaRepository<ApiOutbox, Long> {

    @Query(value = "SELECT * FROM ApiOutbox a WHERE a.outboxStatus = :outboxStatus", nativeQuery = true)
    List<ApiOutbox> findByStatus(OutboxStatus outboxStatus);

    @Query(value = "SELECT * FROM ApiOutbox a WHERE a.createdAt < :now AND a.outboxStatus = :outboxStatus", nativeQuery = true)
    Collection<Object> findByCreatedAtBefore(LocalDateTime now, OutboxStatus outboxStatus);

}
