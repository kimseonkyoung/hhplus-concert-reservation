package kr.hhplus.be.server.interfaces.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRequest {
    private Long userId;  // 사용자 ID
    private Integer amount; // 충전 금액


    public BalanceRequest(long userId, int amount) {
        this.userId = userId;
        this.amount = amount;
    }
}