package kr.hhplus.be.server.domain.reservation.repository;

import kr.hhplus.be.server.domain.common.dto.PaymentServiceResponse;
import kr.hhplus.be.server.domain.reservation.Payment;

public interface PaymentRepository  {

    Payment paymentSave(Payment payment);
}
