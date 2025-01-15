package kr.hhplus.be.server.domain.concert;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.log.DomainLogger;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "concert")
@Setter
@Getter
@DomainLogger
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Column(name = "concert_name", nullable = false)
    private String title;

    public Concert() {

    }

    private Concert(Long concertId, String title) {
        this.concertId = concertId;
        this.title = title;
    }

    // 생성자를 팩토리 메서드로 생성
    public static Concert create(Long ConcertId, String title) {
        return new Concert(ConcertId, title);
    }
}
