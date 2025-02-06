package kr.hhplus.be.server.domain.common.mapper;

import kr.hhplus.be.server.domain.common.dto.ReservationSearviceRequest;
import kr.hhplus.be.server.domain.common.dto.ReservationServiceResponse;
import kr.hhplus.be.server.domain.reservation.Reservation;

public class ReservationEntityConverter {

    public static ReservationServiceResponse toServiceReservationResponse(Reservation reservations) {
        return new ReservationServiceResponse(
                reservations.getReservationId(),
                reservations.getSeatId(),
                reservations.getUserId(),
                reservations.getExpiredAt(),
                reservations.getStatus()
        );
    }
    public static Reservation toEntityReservationRequest(ReservationSearviceRequest reservationRequest) {
        return new Reservation(
                reservationRequest.getSeatId(),
                reservationRequest.getSchedule_id(),
                reservationRequest.getUserId()
        );
    }
}
