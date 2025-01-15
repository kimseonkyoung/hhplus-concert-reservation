package kr.hhplus.be.server.domain.mapper.service;

import kr.hhplus.be.server.domain.concert.Concert;
import kr.hhplus.be.server.domain.concert.ConcertSchedule;
import kr.hhplus.be.server.domain.concert.Seat;
import kr.hhplus.be.server.domain.service.dto.*;

import java.util.ArrayList;
import java.util.List;

public class ConcertEntityConverter {

    public static List<ConcertList> toServiceConcertList(List<Concert> concerts) {
        List<ConcertList> concertLists = new ArrayList<>();
        for (Concert concert : concerts) {
            concertLists.add(new ConcertList(concert.getConcertId(), concert.getTitle()));
        }
        return concertLists;
    }

    public static ConcertServiceResponse toServiceResponse(List<Concert> concerts, int totalCount, int offset, int limit) {
        List<ConcertList> concertList = toServiceConcertList(concerts);
        return ConcertServiceResponse.create(totalCount, offset, limit, concertList);
    }

    public static List<ConcertDateList> toServiceConcertDatesList(List<ConcertSchedule> dates) {
        List<ConcertDateList> concertLists = new ArrayList<>();
        for (ConcertSchedule concertSchedule : dates) {
            concertLists.add(new ConcertDateList(concertSchedule.getConcertId(), concertSchedule.getDate()));
        }
        return concertLists;
    }

    public static ConcertServiceDatesResponse toServiceDatesResponse(List<ConcertSchedule> dates, int totalCount, int offset, int limit) {
        List<ConcertDateList> concertDates = toServiceConcertDatesList(dates);
        return ConcertServiceDatesResponse.create(totalCount, offset, limit, concertDates);
    }

    public static List<SeatServiceResponse> toServiceSeatsResponse(List<Seat> seats) {
        return SeatServiceResponse.create(seats);
    }
}
