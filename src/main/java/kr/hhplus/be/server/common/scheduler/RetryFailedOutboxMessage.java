package kr.hhplus.be.server.common.scheduler;


import kr.hhplus.be.server.application.kafka.KafkaProducer;
import kr.hhplus.be.server.domain.apioutbox.ApiOutbox;
import kr.hhplus.be.server.domain.apioutbox.OutboxStatus;
import kr.hhplus.be.server.domain.apioutbox.repository.ApiOutboxRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RetryFailedOutboxMessage {
    private final ApiOutboxRepository apiOutboxRepository;
    private final KafkaProducer kafkaProducer;


    public RetryFailedOutboxMessage(ApiOutboxRepository apiOutboxRepository, KafkaProducer kafkaProducer) {
        this.apiOutboxRepository = apiOutboxRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Scheduled(fixedRate = 30000) // 30초마다 실행
    @Transactional
    public void retryFailedMessages() {
        List<ApiOutbox> failedMessages = apiOutboxRepository.findByStatus(OutboxStatus.SEND_FAILED);
        List<ApiOutbox> stuckMessages = apiOutboxRepository.findByStatus(OutboxStatus.INIT)
                .stream()
                .filter(msg -> msg.getCreateAt().isBefore(LocalDateTime.now().minusMinutes(10)))
                .collect(Collectors.toList());

        List<ApiOutbox> retryMessages = new ArrayList<>();
        retryMessages.addAll(failedMessages);
        retryMessages.addAll(stuckMessages);

        for (ApiOutbox outbox : retryMessages) {
            kafkaProducer.sendMessage("payment-topic", outbox.getPayload());
            outbox.markAsSuccess();
            apiOutboxRepository.save(outbox);
        }

    }
}
