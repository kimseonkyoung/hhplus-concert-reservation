package kr.hhplus.be.server.domain.service.dto;

import kr.hhplus.be.server.domain.concert.Seat;
import kr.hhplus.be.server.domain.concert.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatServiceResponse {
    private Long seatId;
    private Long concertScheduleId;
    private int no;
    private int price;
    private SeatStatus status;

    public SeatServiceResponse(Seat seat) {
        this.seatId = seat.getSeatId();
        this.concertScheduleId = seat.getConcertScheduleId();
        this.no = seat.getNo();
        this.price = seat.getPrice();
        this.status = seat.getStatus();
    }

    // 팩토리 메서드
    public static List<SeatServiceResponse> create(List<Seat> seats) {
        List<SeatServiceResponse> response = new ArrayList<>();
        for (Seat seat : seats) {
            response.add(new SeatServiceResponse(seat));
        }
        return response;
    }
}
