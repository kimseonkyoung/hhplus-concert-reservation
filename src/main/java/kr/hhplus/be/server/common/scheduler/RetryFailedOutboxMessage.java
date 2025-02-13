package kr.hhplus.be.server.common.scheduler;


import kr.hhplus.be.server.domain.apioutbox.ApiOutbox;
import kr.hhplus.be.server.domain.apioutbox.OutboxStatus;
import kr.hhplus.be.server.domain.apioutbox.repository.ApiOutboxRepository;
import kr.hhplus.be.server.domain.common.dto.ReservationServiceResponse;
import kr.hhplus.be.server.utils.DataPlatFormApiClient;
import kr.hhplus.be.server.utils.JsonUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RetryFailedOutboxMessage {
    private final ApiOutboxRepository apiOutboxRepository;
    private final DataPlatFormApiClient dataPlatFormApiClient;

    public RetryFailedOutboxMessage(ApiOutboxRepository apiOutboxRepository, DataPlatFormApiClient dataPlatFormApiClient) {
        this.apiOutboxRepository = apiOutboxRepository;
        this.dataPlatFormApiClient = dataPlatFormApiClient;
    }

    @Scheduled(cron = "0/5 * * * * *")
    @Transactional
    public void retryFailedOutbox() {
        List<ApiOutbox> failedMessages = apiOutboxRepository.findByStatus(OutboxStatus.FAILED);

        for (ApiOutbox message : failedMessages) {
            try {
                ReservationServiceResponse response = JsonUtil.fromJson(message.getPayload(), ReservationServiceResponse.class);
                boolean success = dataPlatFormApiClient.sendReservationDataToPlatform(response);
                if (success) {
                    message.markAsCompleted();
                } else {
                    message.markAsFailed();
                }
            } catch (Exception e) {
                message.markAsFailed();
                apiOutboxRepository.save(message);
            }
        }
    }
}
