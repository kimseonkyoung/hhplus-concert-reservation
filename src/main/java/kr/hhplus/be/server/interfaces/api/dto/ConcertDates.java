package kr.hhplus.be.server.interfaces.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertDates {
    private Long scheduleId;
    private LocalDateTime dates;
}