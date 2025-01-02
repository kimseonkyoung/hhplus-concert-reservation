package kr.hhplus.be.server.interfaces.api.dto;

import lombok.Data;

@Data
public class BalanceChargeRequest {
    private Long userId;  // 사용자 ID
    private Integer amount; // 충전 금액
}