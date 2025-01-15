package kr.hhplus.be.server.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.domain.concert.SeatStatus;
import kr.hhplus.be.server.domain.common.dto.SeatServiceResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "콘서트 좌석 조회 응답 데이터")
public class SeatResponse {

    @Schema(description = "좌석 ID", example = "1L")
    private Long seatId;

    @Schema(description = "콘서트 스케줄 ID", example = "1L")
    private Long scheduleId;

    @Schema(description = "좌석 번호", example = "12")
    private int seatNo;

    @Schema(description = "좌석 가격", example = "10000")
    private int seatPrice;

    @Schema(description = "좌석 상태", example = "SUCCESS")
    private SeatStatus status;

    public SeatResponse(SeatServiceResponse serviceResponse) {
        this.seatId = serviceResponse.getSeatId();
        this.scheduleId = serviceResponse.getConcertScheduleId();
        this.seatNo = serviceResponse.getNo();
        this.seatPrice = serviceResponse.getPrice();
        this.status = serviceResponse.getStatus();
    }
}