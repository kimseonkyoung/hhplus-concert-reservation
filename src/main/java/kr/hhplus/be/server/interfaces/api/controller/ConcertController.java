package kr.hhplus.be.server.interfaces.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.api.dto.ConcertResponse;
import kr.hhplus.be.server.interfaces.api.dto.SeatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Sample API", description = "Sample API for Swagger Integration")
@RequestMapping("/api/concert")
public class ConcertController {

    @GetMapping("/availableDates")
    @Operation(summary = "Get sample data", description = "This API returns sample data.")
    public ResponseEntity<List<ConcertResponse>> getAvailableConcertDates() {
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