package kr.hhplus.be.server.domain.token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class Token {

    private Long tokenId;
    private String tokenUuid;
    private TokenStatus status;
    private LocalDateTime createdAt;

    public Token() {
    }

    public Token(String tokenUuid, TokenStatus status, LocalDateTime createdAt) {
        this.tokenUuid = tokenUuid;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Token createActive(String tokenUuid, LocalDateTime createdAt) {
        return new Token(tokenUuid, TokenStatus.ACTIVE, createdAt);
    }

    public static Token createWait(String tokenUuid, LocalDateTime createdAt) {
        return new Token(tokenUuid, TokenStatus.WAIT, createdAt);
    }
}
