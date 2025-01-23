package kr.hhplus.be.server.domain.common.dto;

import kr.hhplus.be.server.domain.concert.SeatStatus;
import kr.hhplus.be.server.domain.reservation.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationServiceResponse {
    private Long reservationId;
    private Long userId;
    private Long seatId;
    private ReservationStatus status;
    private LocalDateTime expiredAt;

    public ReservationServiceResponse(Long reservationId, Long seatId, Long userId, LocalDateTime expiredAt, ReservationStatus status) {
        this.reservationId = reservationId;
        this.seatId = seatId;
        this.userId = userId;
        this.status = status;
        this.expiredAt = expiredAt;
    }
}
