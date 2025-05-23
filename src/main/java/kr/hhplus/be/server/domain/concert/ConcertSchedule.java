package kr.hhplus.be.server.domain.concert;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kr.hhplus.be.server.common.log.DomainLogger;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "concert_schedule")
@Setter
@Getter
@DomainLogger
public class ConcertSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long scheduleId;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public ConcertSchedule(Long concertId, LocalDateTime dates) {
        this.concertId = concertId;
        this.date = dates;
    }

    public ConcertSchedule() {

    }

    public static ConcertSchedule create(Long concertId, LocalDateTime dates) {
        return new ConcertSchedule(concertId, dates);
    }
}
