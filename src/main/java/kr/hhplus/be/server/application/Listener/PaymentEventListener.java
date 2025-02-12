package kr.hhplus.be.server.application.Listener;
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
    private final DataPlatFormApiClient dataPlatFormApiClient;

    public PaymentEventListener(DataPlatFormApiClient dataPlatFormApiClient) {
        this.dataPlatFormApiClient = dataPlatFormApiClient;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendReservationDataToPlatform(PaymentCompletedEvent event) {
        try {
            dataPlatFormApiClient.sendReservationDataToPlatform(event.getReservationServiceResponse()); // 여기가 문제
        } catch (Exception e) {
            log.error("데이터 플랫폼 전송 실패: {}", e.getMessage());
        }
    }

}
