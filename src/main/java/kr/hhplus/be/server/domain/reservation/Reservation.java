package kr.hhplus.be.server.domain.reservation;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.log.DomainLogger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@DomainLogger
@Table(name = "reservation")
public class Reservation {

    public static final int EXPIRED_TIME = 5;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "confirm_at")
    private LocalDateTime comfirmAt;

    @Version
    @ColumnDefault("0")
    private Long version;

    public Reservation() {

    }

    private Reservation(long reservationId, ReservationStatus seatStatus) {
        this.reservationId = reservationId;
        this.status = seatStatus;
    }

    public Reservation(Long seatId, Long scheduleId, Long userId) {
        this.seatId = seatId;
        this.scheduleId = scheduleId;
        this.userId = userId;
    }

    public static Reservation create(long reservationId) {
        return new Reservation(reservationId, ReservationStatus.PENDING);
    }

    @PrePersist
    protected void expiredTimeCreate() {
        this.createdAt = LocalDateTime.now();
        this.expiredAt = createdAt.plusMinutes(EXPIRED_TIME);
        this.status = ReservationStatus.PENDING;
    }

    public void confirmReservation() {
        if (this.status == ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Reservation is already confirmed.");
        }
        this.status = ReservationStatus.CONFIRMED;
        this.comfirmAt = LocalDateTime.now();
    }
}
