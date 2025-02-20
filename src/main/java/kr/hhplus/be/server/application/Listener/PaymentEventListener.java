package kr.hhplus.be.server.application.Listener;
import kr.hhplus.be.server.application.kafka.KafkaProducer;
import kr.hhplus.be.server.domain.apioutbox.ApiOutbox;
import kr.hhplus.be.server.domain.apioutbox.OutboxStatus;
import kr.hhplus.be.server.domain.apioutbox.repository.ApiOutboxRepository;
import kr.hhplus.be.server.domain.event.PaymentCompletedEvent;

import kr.hhplus.be.server.utils.DataPlatFormApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class PaymentEventListener {
    private final KafkaProducer kafkaProducer;
    private final ApiOutboxRepository apiOutboxRepository;

    public PaymentEventListener(KafkaProducer kafkaProducer, ApiOutboxRepository apiOutboxRepository) {
        this.kafkaProducer = kafkaProducer;
        this.apiOutboxRepository = apiOutboxRepository;
    }

    // 1. Outbox 저장 (트랜잭션 중)
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveOutbox(PaymentCompletedEvent event) {
        apiOutboxRepository.save(event.getApiOutbox());
    }

    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendReservationDataToPlatform(PaymentCompletedEvent event) {
        ApiOutbox outbox = apiOutboxRepository.findById(event.getApiOutbox().getId()).orElse(null);
        if (outbox == null || outbox.getStatus() != OutboxStatus.INIT) return;

        outbox.setStatus(OutboxStatus.PROCESSING);
        apiOutboxRepository.save(outbox);
        try {
            kafkaProducer.sendMessage("payment-topic", outbox.getPayload());
            outbox.markAsSuccess();
        } catch (Exception e) {
            log.error("Kafka 전송 실패: {}", e.getMessage());
            outbox.markAsFailed();
        }
        apiOutboxRepository.save(outbox);
    }
}
