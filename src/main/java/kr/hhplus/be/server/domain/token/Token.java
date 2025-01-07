package kr.hhplus.be.server.domain.token;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Token")
@Setter
@Getter
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    private Long tokenId;

    @Column(name = "token_uuid", nullable = false, unique = true)
    private String tokenUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TokenStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    public Token() {

    }

    public Token(String tokenUuid, TokenStatus status, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.tokenUuid = tokenUuid;
        this.status = status;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    // 생성자를 팩토리 메서드로 생성
    public static Token create(String tokenUuid, TokenStatus status, LocalDateTime createdAt, LocalDateTime expiredAt) {
        return new Token(tokenUuid, status, createdAt, expiredAt);
    }

}
