package kr.hhplus.be.server.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "콘서트 날짜 조회 응답 데이터")
public class ConcertDatesResponse {

    @Schema(description = "페이지 오프셋 (조회 시작 위치)", example = "0")
    private int offset;

    @Schema(description = "페이지 당 조회할 데이터 수", example = "10")
    private int limit;

    @Schema(description = "총 데이터 수", example = "100")
    private int totalCount;

    @Schema(description = "콘서트 날짜 목록", example = "[{\"date\":\"2025-01-10\"}, {\"date\":\"2025-01-11\"}]")
    private List<ConcertDates> dates; // 개별 콘서트 리스트

    public ConcertDatesResponse(int totalCount, int offset, int limit, List<ConcertDates> dates) {
        this.totalCount = totalCount;
        this.offset = offset;
        this.limit = limit;
        this.dates = dates;
    }
}
