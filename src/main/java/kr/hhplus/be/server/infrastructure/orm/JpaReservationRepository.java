package kr.hhplus.be.server.infrastructure.orm;

import kr.hhplus.be.server.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {
}
