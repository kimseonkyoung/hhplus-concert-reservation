package kr.hhplus.be.server.domain.concert;

import kr.hhplus.be.server.domain.concert.repository.ConcertRepository;
import kr.hhplus.be.server.domain.concert.repository.ConcertScheduleRepository;
import kr.hhplus.be.server.domain.concert.repository.SeatRepository;
import kr.hhplus.be.server.domain.mapper.service.ConcertEntityConverter;
import kr.hhplus.be.server.domain.service.dto.ConcertServiceDatesResponse;
import kr.hhplus.be.server.domain.service.dto.ConcertServiceResponse;
import kr.hhplus.be.server.domain.service.dto.SeatServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final SeatRepository seatRepository;

    /**
     * 콘서트 목록을 조회하는 service 메서드입니다.
     * @param offset
     * @param limit
     * @return
     */
    public ConcertServiceResponse getConcertList(int offset, int limit) {
        // 1. 페이징된 콘서트 목록 반환
        List<Concert> concerts = concertRepository.findAll(offset, limit);
        //2. 총 데이터 개수 조회
        int totalCount = concertRepository.getServiceTotalCount();

        ConcertServiceResponse serviceResponse = ConcertEntityConverter.toServiceResponse(concerts, totalCount, offset, limit);
        return serviceResponse;
    }

    /**
     * 해당 콘서트의 날짜를 조회하는 service 메서드입니다.
     * @param offset
     * @param limit
     * @return
     */
    public ConcertServiceDatesResponse getConcertDateList(Long concertId, int offset, int limit) {
        // 1. 페이징된 콘서트 날짜 목록 반환
        List<ConcertSchedule> concertSchedules = concertScheduleRepository.findAllDates(concertId, offset, limit);
        //2. 총 데이터 개수 조회
        int totalCount = concertScheduleRepository.getDatesTotalCount();

        ConcertServiceDatesResponse serviceResponse = ConcertEntityConverter.toServiceDatesResponse(concertSchedules, totalCount, offset, limit);
        return serviceResponse;
    }

    public List<SeatServiceResponse> getSeatsForDate(Long concertScheduleId) {
        // 1. 스케줄 아이디를 토대로 seat 테이블에서 예약 가능한 좌석 조회
        List<Seat> seatList = seatRepository.getSeatsForDate(concertScheduleId);
        List<SeatServiceResponse> serviceResponse = ConcertEntityConverter.toServiceSeatsResponse(seatList);
        return serviceResponse;
    }
}
