package kr.hhplus.be.server.interfaces.api.controller;

import kr.hhplus.be.server.interfaces.api.dto.ConcertResponse;
import kr.hhplus.be.server.interfaces.api.dto.SeatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/concert")
public class ConcertController {

    @GetMapping("")
    public ResponseEntity<List<ConcertResponse>> getConcertList(){
        return ResponseEntity.ok(List.of(
                new ConcertResponse( 1L, "김영웅의 콘서트"),
                new ConcertResponse( 2L,"에일리의 콘서트"),
                new ConcertResponse( 3L,"김연자의 콘서트"),
                new ConcertResponse( 4L,"실리카겔 콘서트")
        ));
    }


    @GetMapping("/availableDates")
    public ResponseEntity<List<ConcertResponse>> getAvailableConcertDates(@RequestParam("concertId") String concertId) {
        return ResponseEntity.ok(List.of(
            new ConcertResponse( "2024-12-01"),
            new ConcertResponse( "2024-12-02"),
            new ConcertResponse( "2024-12-03"),
            new ConcertResponse( "2024-12-04")
        ));
    }

    @GetMapping("/seats")
    public ResponseEntity<List<SeatResponse>> getSeatsForDate(@RequestParam("date") String date) {
        List<SeatResponse> seatList = List.of(
                new SeatResponse(1L, "A1", true),
                new SeatResponse(2L, "A2", false),
                new SeatResponse(3L, "A3", true)
        );
        return ResponseEntity.ok(seatList);
    }
}