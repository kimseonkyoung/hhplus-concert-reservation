package kr.hhplus.be.server.interfaces.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertResponse {
    private Long concertId;
    private Long scheduleId;
    private String concertName;
    private String concertDate;

    public ConcertResponse(String concertDate) {
    }
    public ConcertResponse(Long concertId, String concertDate ){

    }
}