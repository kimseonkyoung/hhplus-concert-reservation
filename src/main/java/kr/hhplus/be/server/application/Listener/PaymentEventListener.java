package kr.hhplus.be.server.application.Listener;
import kr.hhplus.be.server.domain.apioutbox.ApiOutbox;
import kr.hhplus.be.server.domain.apioutbox.repository.ApiOutboxRepository;
import kr.hhplus.be.server.domain.event.PaymentCompletedEvent;

import kr.hhplus.be.server.infrastructure.repository.Apioutbox.ApiOutboxRepositoryImpl;
import kr.hhplus.be.server.utils.DataPlatFormApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class PaymentEventListener {
    private final DataPlatFormApiClient dataPlatFormApiClient;
    private final ApiOutboxRepository apiOutboxRepository;

    public PaymentEventListener(DataPlatFormApiClient dataPlatFormApiClient, ApiOutboxRepository apiOutboxRepository) {
        this.dataPlatFormApiClient = dataPlatFormApiClient;
        this.apiOutboxRepository = apiOutboxRepository;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendReservationDataToPlatform(PaymentCompletedEvent event) {
        ApiOutbox outbox = apiOutboxRepository.findById(event.getApiOutbox().getId()).orElse(null);

        try {
            boolean success = dataPlatFormApiClient.sendReservationDataToPlatform(event.getReservationServiceResponse());
            if (success) {
                outbox.markAsCompleted();
            } else {
                outbox.markAsFailed();
            }
        } catch (Exception e) {
            log.error("데이터 플랫폼 전송 실패: {}", e.getMessage());
            outbox.markAsFailed();
            apiOutboxRepository.save(outbox);
        }
    }

}
