package kr.hhplus.be.server.domain.common.dto;

import kr.hhplus.be.server.domain.reservation.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentServiceResponse {

    private Long paymentId;
    private Long reservationId;
    private PaymentStatus status;
}
