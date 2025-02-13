package kr.hhplus.be.server.domain.event;

import kr.hhplus.be.server.domain.apioutbox.ApiOutbox;
import kr.hhplus.be.server.domain.common.dto.ReservationServiceResponse;

public class PaymentCompletedEvent {
    private final ReservationServiceResponse reservationServiceResponse;
    private final ApiOutbox apiOutbox;

    public PaymentCompletedEvent(ReservationServiceResponse reservationServiceResponse, ApiOutbox apiOutbox) {
        this.reservationServiceResponse = reservationServiceResponse;
        this.apiOutbox = apiOutbox;
    }

    public ReservationServiceResponse getReservationServiceResponse() {
        return reservationServiceResponse;
    }

    public ApiOutbox getApiOutbox() {
        return apiOutbox;
    }
}
