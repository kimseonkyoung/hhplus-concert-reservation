package kr.hhplus.be.server.interfaces.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ConcertDatesResponse {
    private int offset;
    private int limit;
    private int totalCount;
    private List<ConcertDates> dates; // 개별 콘서트 리스트

    public ConcertDatesResponse(int totalCount, int offset, int limit, List<ConcertDates> dates) {
        this.totalCount = totalCount;
        this.offset = offset;
        this.limit = limit;
        this.dates = dates;
    }
}
