package kr.hhplus.be.server.infrastructure.orm.Apioutbox;

import kr.hhplus.be.server.domain.apioutbox.ApiOutbox;
import kr.hhplus.be.server.domain.apioutbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaApiOutboxRepository extends JpaRepository<ApiOutbox, Long> {
    @Query("SELECT a.payload from ApiOutbox a where a.status = :outboxStatus")
    List<ApiOutbox> findByStatus(OutboxStatus outboxStatus);
}
