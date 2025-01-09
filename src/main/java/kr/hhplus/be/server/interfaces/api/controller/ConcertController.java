package kr.hhplus.be.server.interfaces.api.controller;

import kr.hhplus.be.server.application.facade.ReservationFacade;
import kr.hhplus.be.server.domain.service.dto.UserServiceResponse;
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
public class ConcertController {

    private final ReservationFacade facade;

    @GetMapping("/list")
    public ResponseEntity<ConcertResponse> getConcertList(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        ConcertResponse response = facade.getConcertList(offset, limit);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/availableDates")
    public ResponseEntity<ConcertDatesResponse> getAvailableConcertDates(
            @RequestParam(name = "concertId") Long concertId,
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        ConcertDatesResponse response = facade.getConcertDates(concertId, offset, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seats")
    public ResponseEntity<List<SeatResponse>> getSeatsForDate(@RequestParam(name = "concertScheduleId") Long concertScheduleId) {
        List<SeatResponse> response = facade.getSeatsForDate(concertScheduleId);
        return ResponseEntity.ok(response);
    }
}