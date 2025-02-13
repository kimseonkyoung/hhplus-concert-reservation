package kr.hhplus.be.server.domain.apioutbox;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_outbox")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OutboxStatus status;

    @Column(name = "payload", columnDefinition = "JSON", nullable = false)
    private String payload;

    public void markAsFailed() {
        this.status = OutboxStatus.FAILED;
    }

    public void markAsCompleted() {
        this.status = OutboxStatus.COMPLETED;
    }
}
