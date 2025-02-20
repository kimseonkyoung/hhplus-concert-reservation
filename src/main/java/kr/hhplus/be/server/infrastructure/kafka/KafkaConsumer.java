package kr.hhplus.be.server.infrastructure.kafka;

import kr.hhplus.be.server.domain.apioutbox.repository.ApiOutboxRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaConsumer {
    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void consumeMessage(String message) {
        log.info("Received message: {}", message);
    }

    @KafkaListener(topics = "payment-topic", groupId = "payment-group")
    public void consume(String message) {
        log.info("Received message: {}", message);
        // 여기서 추가적으로 필요한 처리를 수행 가능
    }
}
