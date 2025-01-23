package kr.hhplus.be.server.domain.User;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.log.DomainLogger;
import kr.hhplus.be.server.domain.common.exception.InsufficientBalanceException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Setter
@Getter
@DomainLogger
@Table(name = "User")
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "balance_value", nullable = false)
    private Integer balance;

    @Version
    @ColumnDefault("0")
    private Long version;

    public User() {
    }

    public User(Long userId, Integer balance) {
        this.userId = userId;
        this.balance = balance;
    }

    // 생성자를 팩토리 메서드로 생성
    public static User create(Long userId, Integer balance) {
        return new User(userId, balance);
    }

    public void chargeBalance(int amount) {
        if (amount <= 0) {
            throw new IllegalStateException("중전 금액은 1원 이상이여야합니다.");
        }
        this.balance += amount;
    }

    public void deductBalance(int price) {
        if (this.balance < price) {
            throw new InsufficientBalanceException("잔액이 부족합니다.");
        }
        this.balance -= price;
    }
}
