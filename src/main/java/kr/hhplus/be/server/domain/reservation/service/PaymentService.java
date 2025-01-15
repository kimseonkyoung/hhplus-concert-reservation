package kr.hhplus.be.server.domain.reservation.service;

import kr.hhplus.be.server.common.log.AllRequiredLogger;
import kr.hhplus.be.server.domain.common.dto.PaymentServiceResponse;
import kr.hhplus.be.server.domain.common.mapper.PaymentEntityConverter;
import kr.hhplus.be.server.domain.reservation.Payment;
import kr.hhplus.be.server.domain.reservation.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@AllRequiredLogger
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceResponse paymentSave(Long reservationId) {
        // 1. 결제 정보 셋팅
        Payment payment = new Payment();
        payment.createPaymentInfo(reservationId, LocalDateTime.now());

        // 2. 결제 정보 저장
        Payment savePayment = paymentRepository.paymentSave(payment);
        PaymentServiceResponse response = PaymentEntityConverter.toServicePaymentResponse(savePayment);

        return response;
    }
}
