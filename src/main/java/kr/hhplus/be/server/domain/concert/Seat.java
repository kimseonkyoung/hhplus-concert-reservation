package kr.hhplus.be.server.domain.concert;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.log.DomainLogger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Seat")
@Setter
@Getter
@AllArgsConstructor
@DomainLogger
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Column(name = "concert_schedule_id", nullable = false)
    private Long concertScheduleId;

    @Column(name = "no", nullable = false)
    private int no;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "status", nullable = false)
    private SeatStatus status;

    public Seat() {

    }

    public Seat(long seatId, Long concertScheduleId, SeatStatus status) {
        this.seatId = seatId;
        this.concertScheduleId = concertScheduleId;
        this.status = status;
    }

    public static Seat create(long seatId, Long concertScheduleId, SeatStatus status) {
        return new Seat(seatId, concertScheduleId, status);
    }

    public void updateSeatCompleted() {
        this.status = SeatStatus.COMPLETED;
    }

    public void updateSeatProgress() {
        this.status = SeatStatus.PROGRESS;
    }
}
