package kr.hhplus.be.server.interfaces.api.dto;

import kr.hhplus.be.server.domain.reservation.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponse {

    private Long paymentId;
    private Long reservationId;
    private PaymentStatus status;

    public PaymentResponse(Long paymentId, Long reservationId, PaymentStatus status) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.status = status;
    }
}
