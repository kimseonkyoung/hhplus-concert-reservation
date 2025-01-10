package kr.hhplus.be.server.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "예약 관련 응답 데이터")
public class ReservationResponse {

    @Schema(description = "예약 ID", example = "1L")
    private Long reservationId;

    @Schema(description = "좌석 ID", example = "1L")
    private Long seatId;

    @Schema(description = "유저 ID", example = "1L")
    private Long userId;

    @Schema(description = "예약 상태", example = "SUCCESS")
    private String status;

    public ReservationResponse(long l, Long seatId, String success) {
    }
}
