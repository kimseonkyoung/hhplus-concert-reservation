package kr.hhplus.be.server.interfaces.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    private Long scheduleId;
    private Long seatId;
    private Long userId;
    private String userUuid;

    public ReservationRequest(long scheduleId, long seatId, Long userId) {
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.userId = userId;
    }
}
