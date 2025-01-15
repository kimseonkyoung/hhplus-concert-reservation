package kr.hhplus.be.server.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "충전 관련 응답 데이터")
public class BalanceResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "잔액", example = "10000")
    private Integer balance;

    @Schema(description = "충전액", example = "5000")
    private Integer amount;

    public BalanceResponse(Long userId, int balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public BalanceResponse(long userId, int balance, int amount) {
        this.userId = userId;
        this.balance = balance;
        this.amount = amount;
    }
}