package kr.hhplus.be.server.domain.common.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceResponse {

    private Long userId;
    private Integer balance;
    private Integer amount;

    public UserServiceResponse(long userId, int balance) {
        this.userId = userId;
        this.balance = balance;
    }
    public UserServiceResponse(long userId, int balance, int amount) {
        this.userId = userId;
        this.balance = balance;
        this.amount = amount;
    }
}
