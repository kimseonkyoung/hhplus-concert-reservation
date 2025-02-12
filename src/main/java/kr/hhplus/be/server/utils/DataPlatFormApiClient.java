package kr.hhplus.be.server.utils;

import kr.hhplus.be.server.domain.common.dto.ReservationServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataPlatFormApiClient {
    public void sendReservationDataToPlatform(ReservationServiceResponse response){
        log.info("해당 예약정보를 전송했습니다.", response);
    }

}
