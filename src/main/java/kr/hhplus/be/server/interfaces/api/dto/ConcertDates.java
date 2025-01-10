package kr.hhplus.be.server.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "콘서트 날짜 데이터")
public class ConcertDates {

    @Schema(description = "콘서트 스케줄 ID", example = "1L")
    private Long scheduleId;

    @Schema(description = "콘서트 공연 날짜", example = "2025-01-19 00:00:00")
    private LocalDateTime dates;
}