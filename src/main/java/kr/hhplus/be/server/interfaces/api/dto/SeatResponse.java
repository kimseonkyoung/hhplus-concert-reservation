package kr.hhplus.be.server.interfaces.api.dto;

import kr.hhplus.be.server.domain.concert.SeatStatus;
import kr.hhplus.be.server.domain.service.dto.SeatServiceResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {
    private Long seatId;
    private Long concertScheduleId;
    private int seatNo;
    private int seatPrice;
    private SeatStatus status;

    public SeatResponse(SeatServiceResponse serviceResponse) {
        this.seatId = serviceResponse.getSeatId();
        this.concertScheduleId = serviceResponse.getConcertScheduleId();
        this.seatNo = serviceResponse.getNo();
        this.seatPrice = serviceResponse.getPrice();
        this.status = serviceResponse.getStatus();
    }
}