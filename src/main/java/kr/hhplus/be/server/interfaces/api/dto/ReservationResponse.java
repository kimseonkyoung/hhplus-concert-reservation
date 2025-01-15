package kr.hhplus.be.server.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.domain.concert.SeatStatus;
import kr.hhplus.be.server.domain.reservation.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "예약 관련 응답 데이터")
public class ReservationResponse {

    @Schema(description = "예약 ID", example = "1L")
    private Long reservationId;

    @Schema(description = "만료 시간", example = "2025-01-12 12:00")
    private LocalDateTime expiredAt;

    @Schema(description = "예약 상태", example = "PROGRESS")
    private ReservationStatus status;

    public ReservationResponse(Long reservationId, LocalDateTime expiredAt, ReservationStatus status) {
        this.reservationId = reservationId;
        this.expiredAt = expiredAt;
        this.status = status;
    }
}
