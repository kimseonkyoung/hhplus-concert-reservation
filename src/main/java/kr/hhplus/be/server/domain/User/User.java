package kr.hhplus.be.server.domain.User;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.log.DomainLogger;
import kr.hhplus.be.server.domain.common.exception.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@DomainLogger
@Table(name = "Balance")
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "balance_value", nullable = false)
    private Integer balance;

    public User() {}

    // 생성자를 팩토리 메서드로 생성
    public static User create(Long userId, Integer balance) {
        return new User(userId, balance);
    }

    public int chargeBalance(int amount) {
        if (amount <= 0) {
            throw new IllegalStateException("중전 금액은 1원 이상이여야합니다.");
        }
        this.balance += amount;
        return amount;
    }

    public Integer deductBalance(int price) {
        if (this.balance < price) {
            throw new InsufficientBalanceException("잔액이 부족합니다.");
        }
        this.balance -= price;
        return balance;
    }
}
