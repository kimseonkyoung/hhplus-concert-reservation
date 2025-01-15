package kr.hhplus.be.server.infrastructure.repository.Reservation;

import kr.hhplus.be.server.common.log.AllRequiredLogger;
import kr.hhplus.be.server.domain.reservation.Payment;
import kr.hhplus.be.server.domain.reservation.repository.PaymentRepository;
import kr.hhplus.be.server.infrastructure.orm.Reservation.JpaPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@AllRequiredLogger
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository paymentRepository;

    @Override
    public Payment paymentSave(Payment payment) {
        return paymentRepository.save(payment);
    }
}
