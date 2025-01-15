package kr.hhplus.be.server.domain.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertServiceDatesResponse {
    private int totalCount; // 총 콘서트 수
    private int offset;     // 현재 페이지 시작 위치
    private int limit;      // 한 페이지에 표시할 데이터 수
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private List<ConcertDateList> dates; // 콘서트 스케줄 id, 날짜 목록

    // 팩토리 메서드
    public static ConcertServiceDatesResponse create(int totalCount, int offset, int limit, List<ConcertDateList> dates) {
        return new ConcertServiceDatesResponse(totalCount, offset, limit, dates);
    }
}
