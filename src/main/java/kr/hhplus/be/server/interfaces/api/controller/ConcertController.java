package kr.hhplus.be.server.interfaces.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.facade.ReservationFacade;
import kr.hhplus.be.server.interfaces.api.dto.ConcertDatesResponse;
import kr.hhplus.be.server.interfaces.api.dto.ConcertResponse;
import kr.hhplus.be.server.interfaces.api.dto.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/concerts")
@Tag(name = "Concert API", description = "콘서트 관련 API")
public class ConcertController {

    private final ReservationFacade facade;

    @GetMapping("/list")
    @Operation(summary = "콘서트 목록 조회", description = "예약 가능한 콘서트 목록을 조회합니다.")
    public ResponseEntity<ConcertResponse> getConcertList(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        ConcertResponse response = facade.getConcertList(offset, limit);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/availableDates")
    @Operation(summary = "콘서트 날짜 조회", description = "콘서트 ID를 통해 에약 가능한 콘서트 날짜를 조회합니다.")
    public ResponseEntity<ConcertDatesResponse> getAvailableConcertDates(
            @RequestParam(name = "concertId") Long concertId,
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        ConcertDatesResponse response = facade.getConcertDates(concertId, offset, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seats")
    @Operation(summary = "콘서트 좌석 조회", description = "콘서트 스케줄 ID를 통해 예약 가능한 콘서트 좌석을 조회합니다.")
    public ResponseEntity<List<SeatResponse>> getSeatsForDate(@RequestParam(name = "concertScheduleId") Long concertScheduleId) {
        List<SeatResponse> response = facade.getSeatsForDate(concertScheduleId);
        return ResponseEntity.ok(response);
    }
}