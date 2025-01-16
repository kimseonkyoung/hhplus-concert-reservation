package kr.hhplus.be.server.domain.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSearviceRequest {
    private Long schedule_id;
    private Long seatId;
    private Long userId;
    private String userUuid;

    public ReservationSearviceRequest(Long seatId, Long scheduleId, Long userId) {
        this.seatId = seatId;
        this.schedule_id = scheduleId;
        this.userId = userId;
    }
}
