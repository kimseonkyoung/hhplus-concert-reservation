package kr.hhplus.be.server.domain.reservation.repository;

import kr.hhplus.be.server.domain.concert.SeatStatus;
import kr.hhplus.be.server.domain.reservation.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long reservationId);
}
