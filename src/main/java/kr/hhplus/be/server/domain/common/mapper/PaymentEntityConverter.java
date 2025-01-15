package kr.hhplus.be.server.domain.common.mapper;

import kr.hhplus.be.server.domain.common.dto.PaymentServiceResponse;
import kr.hhplus.be.server.domain.reservation.Payment;

public class PaymentEntityConverter {

    public static PaymentServiceResponse toServicePaymentResponse(Payment payment) {
        return new PaymentServiceResponse(
                payment.getPaymentId(),
                payment.getReservationId(),
                payment.getStatus()
        );
    }
}
