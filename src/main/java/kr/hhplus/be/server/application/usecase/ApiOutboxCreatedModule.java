package kr.hhplus.be.server.application.usecase;

import kr.hhplus.be.server.domain.apioutbox.ApiOutbox;
import kr.hhplus.be.server.domain.apioutbox.OutboxStatus;
import kr.hhplus.be.server.domain.apioutbox.repository.ApiOutboxRepository;
import kr.hhplus.be.server.domain.common.dto.ReservationServiceResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static kr.hhplus.be.server.utils.JsonUtil.convertToJson;

@Component
public class ApiOutboxCreatedModule {

    private final ApiOutboxRepository apiOutboxRepository;

    public ApiOutboxCreatedModule(ApiOutboxRepository apiOutboxRepository) {
        this.apiOutboxRepository = apiOutboxRepository;
    }

    public ApiOutbox CreatedApiOutbox(ReservationServiceResponse response) {
        ApiOutbox apiOutbox = new ApiOutbox();
        apiOutbox.setStatus(OutboxStatus.INIT);
        apiOutbox.setPayload(convertToJson(response));
        apiOutbox.setCreateAt(LocalDateTime.now());
        apiOutboxRepository.save(apiOutbox);
        return apiOutbox;
    }

}
