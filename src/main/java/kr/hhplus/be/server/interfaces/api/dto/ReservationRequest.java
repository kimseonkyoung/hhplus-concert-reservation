package kr.hhplus.be.server.interfaces.api.dto;

import lombok.Data;

@Data
public class ReservationRequest {
    private Long seatId;
    private Long userId;
    private Long userUuid;
}
