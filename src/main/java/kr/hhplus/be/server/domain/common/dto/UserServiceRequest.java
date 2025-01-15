package kr.hhplus.be.server.domain.common.dto;

import lombok.Data;

@Data
public class UserServiceRequest {
    private Long userId;  // 사용자 ID
    private Integer amount; // 충전 금액

    public UserServiceRequest(long userId, int amount) {
        this.userId = userId;
        this.amount = amount;
    }
}
