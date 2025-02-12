package kr.hhplus.be.server.application.publisher;

import kr.hhplus.be.server.domain.common.dto.ReservationServiceResponse;
import kr.hhplus.be.server.domain.event.PaymentCompletedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public PaymentEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishPaymentCompletedEvent(ReservationServiceResponse reservationServiceResponse) {
        applicationEventPublisher.publishEvent(new PaymentCompletedEvent(reservationServiceResponse));
    }
}

