package kr.hhplus.be.server.domain.reservation.service;

import kr.hhplus.be.server.common.log.AllRequiredLogger;
import kr.hhplus.be.server.domain.common.dto.PaymentServiceResponse;
import kr.hhplus.be.server.domain.common.dto.ReservationSearviceRequest;
import kr.hhplus.be.server.domain.common.dto.ReservationServiceResponse;
import kr.hhplus.be.server.domain.common.exception.ReservationNotFoundException;
import kr.hhplus.be.server.domain.common.mapper.ReservationEntityConverter;
import kr.hhplus.be.server.domain.concert.SeatStatus;
import kr.hhplus.be.server.domain.reservation.Reservation;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@AllRequiredLogger
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceResponse reserveSeat(ReservationSearviceRequest reservationRequest) {
        // 1. DTO -> Entity 변환
        Reservation reservation = ReservationEntityConverter.toEntityReservationRequest(reservationRequest);

        // 2. 예약 테이블 임시 점유, 예약 상태 진행중 변경
        Reservation savedReservation = reservationRepository.save(reservation);

        // 3. Entity -> DTO 변환
        return ReservationEntityConverter.toServiceReservationResponse(savedReservation);
    }

    public ReservationServiceResponse findById(Long reservationId) {
        // 1. 예약 진행 중인 정보 조회
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("해당 예약 정보를 찾을 수 없습니다."));

        // 2. reservation entity -> service dto 변환
        ReservationServiceResponse response = ReservationEntityConverter.toServiceReservationResponse(reservation);
        return response;
    }

    public ReservationServiceResponse confirmedReservation(Long reservationId) {
        // 1. 결제 완료 후 예약 확정(상태 변경 및 확정 날짜)
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("해당 예약 정보를 찾을 수 없습니다."));

        reservation.confirmReservation();
        ReservationServiceResponse response = ReservationEntityConverter.toServiceReservationResponse(reservation);
        return response;
    }
}
