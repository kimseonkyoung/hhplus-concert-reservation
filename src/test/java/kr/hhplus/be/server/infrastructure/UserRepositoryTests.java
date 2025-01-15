package kr.hhplus.be.server.infrastructure;

import kr.hhplus.be.server.domain.User.User;
import kr.hhplus.be.server.domain.User.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    /**
     * 해당 테스트는 차후에 docker compose container가 올라가면 테스트 하도록 합니다.
     * 일단 코드만 구현해놓습니다.
     */
    @DisplayName("해당 유저의 잔액을 조회하는 테스트: 성공")
    void test1() {
        //given

    }

    /**
     * 해당 테스트는 차후에 docker compose container가 올라가면 테스트 하도록 합니다.
     * 일단 코드만 구현해놓습니다.
     */
    @DisplayName("해당 유저의 잔액을 충전하는 테스트: 성공")
    void test2() {
        //given


    }
}
