package kr.hhplus.be.server.domain.reservation;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.log.DomainLogger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@DomainLogger
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_At", nullable = false)
    private LocalDateTime updatedAt;

    public Payment() {

    }

    public void createPaymentInfo(Long reservationId, LocalDateTime createdAt) {
        this.reservationId = reservationId;
        this.status = PaymentStatus.SUCCESS;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }
}
