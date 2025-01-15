package kr.hhplus.be.server.infrastructure.orm.Reservation;

import kr.hhplus.be.server.domain.common.dto.PaymentServiceResponse;
import kr.hhplus.be.server.domain.reservation.Payment;
import kr.hhplus.be.server.domain.reservation.PaymentStatus;
import kr.hhplus.be.server.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {

}
