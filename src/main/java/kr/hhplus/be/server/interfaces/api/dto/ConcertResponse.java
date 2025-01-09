package kr.hhplus.be.server.interfaces.api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ConcertResponse {
    private int offset;
    private int limit;
    private int totalCount;
    private List<ConcertItems> concerts; // 개별 콘서트 리스트

    public ConcertResponse(int totalCount, int offset, int limit, List<ConcertItems> concerts) {
        this.totalCount = totalCount;
        this.offset = offset;
        this.limit = limit;
        this.concerts = concerts;
    }
}