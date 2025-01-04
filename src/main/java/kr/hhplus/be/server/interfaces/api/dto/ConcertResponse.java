package kr.hhplus.be.server.interfaces.api.dto;

import lombok.Data;

@Data
public class ConcertResponse {
    private Long scheduleId;
    private String concertName;
    private String concertDate;

    public ConcertResponse(String date) {
    }
}