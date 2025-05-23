package kr.hhplus.be.server.domain.concert;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.log.DomainLogger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Table(name = "Seat")
@Setter
@Getter
@AllArgsConstructor
@DomainLogger
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long seatId;

    @Column(name = "concert_schedule_id", nullable = false)
    private Long concertScheduleId;

    @Column(name = "no", nullable = false)
    private int no;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    private SeatStatus status;

    @Version
    @ColumnDefault("0")
    private Long version;

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
        this.status = SeatStatus.SOLD;
    }

    public void updateSeatProgress() {
        this.status = SeatStatus.PROGRESS;
    }

    public boolean isOccupied() {
        return this.status == SeatStatus.PROGRESS;
    }
}
