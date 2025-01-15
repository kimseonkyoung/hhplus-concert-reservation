package kr.hhplus.be.server.domain.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ConcertScheduleResponse {
        private String date;

        public ConcertScheduleResponse(String date) {
            this.date = date;
        }
    }
}
