package kr.hhplus.be.server.interfaces.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    private Long schedule_id;
    private Long seatId;
    private Long userId;
    private String userUuid;
}
