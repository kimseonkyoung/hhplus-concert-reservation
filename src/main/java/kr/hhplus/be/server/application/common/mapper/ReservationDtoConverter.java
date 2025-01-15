package kr.hhplus.be.server.application.common.mapper;

import kr.hhplus.be.server.domain.common.dto.ReservationSearviceRequest;
import kr.hhplus.be.server.domain.common.dto.ReservationServiceResponse;
import kr.hhplus.be.server.interfaces.api.dto.ReservationRequest;
import kr.hhplus.be.server.interfaces.api.dto.ReservationResponse;

public class ReservationDtoConverter {

    public static ReservationResponse toControllerReservationResponse(ReservationServiceResponse response) {
        return new ReservationResponse(
                response.getReservationId(),
                response.getExpiredAt(),
                response.getStatus()
        );
    }

    public static ReservationSearviceRequest toServiceReservationRequest(ReservationRequest request) {
        return new ReservationSearviceRequest(
                request.getSeatId(),
                request.getSchedule_id(),
                request.getUserId()
        );
    }
}
