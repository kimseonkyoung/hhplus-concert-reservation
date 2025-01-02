package kr.hhplus.be.server.interfaces.api.dto;

import lombok.Data;

@Data
public class BalanceResponse {
    private Long userId;       // 사용자 ID
    private Integer balance;   // 현재 잔액

    public BalanceResponse(Long userId, int i) {
    }
}