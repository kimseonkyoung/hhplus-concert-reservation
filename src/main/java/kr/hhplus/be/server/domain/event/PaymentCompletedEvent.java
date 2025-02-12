package kr.hhplus.be.server.domain.event;

import kr.hhplus.be.server.domain.common.dto.ReservationServiceResponse;

public class PaymentCompletedEvent {
    private final ReservationServiceResponse reservationServiceResponse;

    public PaymentCompletedEvent(ReservationServiceResponse reservationServiceResponse) {
        this.reservationServiceResponse = reservationServiceResponse;
    }

    public ReservationServiceResponse getReservationServiceResponse() {
        return reservationServiceResponse;
    }
}
