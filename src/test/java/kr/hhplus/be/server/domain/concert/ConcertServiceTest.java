package kr.hhplus.be.server.domain.concert;

import kr.hhplus.be.server.domain.common.dto.*;
import kr.hhplus.be.server.domain.concert.repository.ConcertRepository;
import kr.hhplus.be.server.domain.concert.repository.ConcertScheduleRepository;
import kr.hhplus.be.server.domain.concert.repository.SeatRepository;
import kr.hhplus.be.server.domain.concert.service.ConcertService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ConcertService concertService;

    @Test
    @DisplayName("콘서트 목록 조회시 목록 반환")
    void test1() {
        // given
        int offset = 0;
        int limit = 10;

        List<Concert> mockConcertList = Arrays.asList(
                Concert.create(1L, "김연자의 콘서트"),
                Concert.create(2L, "나훈아의 콘서트")
        );
        given(concertRepository.findAll(offset, limit)).willReturn(mockConcertList);
        given(concertRepository.getServiceTotalCount()).willReturn(2);

        // when
        ConcertServiceResponse response = concertService.getConcertList(offset, limit);

        // then
        assertEquals(2, response.getTotalCount());
        assertEquals(offset, response.getOffset());
        assertEquals(limit, response.getLimit());
        assertEquals(2, response.getConcertList().size());
        assertEquals(1L, response.getConcertList().get(0).getId());
        assertEquals("김연자의 콘서트", response.getConcertList().get(0).getTitle());
        assertEquals(2L, response.getConcertList().get(1).getId());
        assertEquals("나훈아의 콘서트", response.getConcertList().get(1).getTitle());

        System.out.println("totalCount: " + response.getTotalCount());
        System.out.println("offset: " + response.getOffset());
        System.out.println("limit: " + response.getLimit());
        for(ConcertList v : response.getConcertList()) {
            System.out.println("Id = " + v.getId());
            System.out.println("Title = " + v.getTitle());
        }
    }
    @Test
    @DisplayName("콘서트 날짜 조회시 목록 반환")
    void test2() {
        // given
        int offset = 0;
        int limit = 10;

        // Mocked Concert 엔티티 리스트
        List<ConcertSchedule> concertSchedules = Arrays.asList(
                ConcertSchedule.create(1L, LocalDateTime.of(2025, 1, 9, 11, 0, 0)),
                ConcertSchedule.create(1L, LocalDateTime.of(2025, 1, 10, 11, 0, 0))
        );

        given(concertScheduleRepository.findAllDates(1L, offset, limit)).willReturn(concertSchedules);
        given(concertScheduleRepository.getDatesTotalCount()).willReturn(2);

        // when
        ConcertServiceDatesResponse response = concertService.getConcertDateList(1L, offset, limit);

        // then
        assertEquals(2, response.getTotalCount());
        assertEquals(offset, response.getOffset());
        assertEquals(limit, response.getLimit());
        assertEquals(2, response.getDates().size());
        assertEquals(1L, response.getDates().get(0).getId());
        assertEquals(LocalDateTime.of(2025, 1, 9, 11, 0, 0), response.getDates().get(0).getDate());
        assertEquals(1L, response.getDates().get(1).getId());
        assertEquals(LocalDateTime.of(2025, 1, 10, 11, 0, 0), response.getDates().get(1).getDate());

        System.out.println("totalCount: " + response.getTotalCount());
        System.out.println("offset: " + response.getOffset());
        System.out.println("limit: " + response.getLimit());
        for (ConcertDateList v : response.getDates()) {
            System.out.println("Id = " + v.getId());
            System.out.println("dates = " + v.getDate());
        }
    }
    @Test
    @DisplayName("콘서트 좌석 조회시 목록 반환")
    void test3() {
        // given
        List<Seat> seats = Arrays.asList(
                Seat.create(1L, 1L, SeatStatus.AVAILABLE),
                Seat.create(2L, 1L, SeatStatus.AVAILABLE),
                Seat.create(3L, 1L, SeatStatus.AVAILABLE),
                Seat.create(4L, 1L, SeatStatus.PROGRESS),
                Seat.create(5L, 1L, SeatStatus.PROGRESS),
                Seat.create(6L, 1L, SeatStatus.PROGRESS),
                Seat.create(7L, 1L, SeatStatus.PROGRESS)
        );
        given(seatRepository.getSeatsForDate(1L)).willReturn(seats);

        // when
        List<SeatServiceResponse> response = concertService.getSeatsForDate(1L);

        // then
        assertEquals(1L, response.get(0).getConcertScheduleId());
        assertEquals(1L, response.get(1).getConcertScheduleId());
        assertEquals(1L, response.get(2).getConcertScheduleId());
        assertEquals(1L, response.get(3).getConcertScheduleId());
        assertEquals(SeatStatus.AVAILABLE, response.get(0).getStatus());
        assertEquals(SeatStatus.AVAILABLE, response.get(1).getStatus());
        assertEquals(SeatStatus.AVAILABLE, response.get(2).getStatus());
        assertEquals(SeatStatus.PROGRESS, response.get(3).getStatus());
    }
}
