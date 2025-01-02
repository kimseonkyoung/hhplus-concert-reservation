package kr.hhplus.be.server.interfaces.api.dto;

import lombok.Data;

@Data
public class SeatResponse {
    private Long seatId;
    private int seatNo;
    private int seatPrice;
    private boolean status_id;

    public SeatResponse(long l, String a1, boolean b) {
    }
}