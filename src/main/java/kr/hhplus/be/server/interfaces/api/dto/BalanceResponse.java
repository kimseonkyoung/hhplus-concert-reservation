package kr.hhplus.be.server.interfaces.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse {
    private Long userId;       // 사용자 ID
    private Integer balance;   // 현재 잔액
    private Integer amount;    // 충전액

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