package kr.hhplus.be.server.infrastructure.orm.Reservation;

import kr.hhplus.be.server.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long reservationId);
    List<Reservation> findAllBySeatId(Long seatId);
}
