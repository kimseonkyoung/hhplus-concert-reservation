package kr.hhplus.be.server.interfaces.api.dto;

import lombok.Data;

@Data
public class ReservationResponse {
    private Long reservationId;
    private Long seatId;
    private Long userId;
    private String status;

    public ReservationResponse(long l, Long seatId, String success) {
    }
}
