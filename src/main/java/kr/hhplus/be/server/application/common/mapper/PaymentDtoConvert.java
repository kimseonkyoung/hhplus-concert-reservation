package kr.hhplus.be.server.application.common.mapper;

import kr.hhplus.be.server.domain.common.dto.PaymentServiceResponse;
import kr.hhplus.be.server.interfaces.api.dto.PaymentResponse;

public class PaymentDtoConvert {
    public static PaymentResponse toControllerPaymentResponse(PaymentServiceResponse response) {
        return new PaymentResponse(
                response.getPaymentId(),
                response.getReservationId(),
                response.getStatus()
        );
    }
}
