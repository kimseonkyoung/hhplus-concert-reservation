package kr.hhplus.be.server.integration;


import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.User.repository.UserRepository;
import kr.hhplus.be.server.domain.User.service.UserService;
import kr.hhplus.be.server.domain.common.dto.UserServiceRequest;
import kr.hhplus.be.server.domain.common.dto.UserServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("충전 한 번 요청시 성공 케이스")
    void test1() {
        // given
        Integer balance = 1000;
        UserServiceRequest userServiceRequest = new UserServiceRequest(1L, 1000);

        // when
        userService.chargeUserBalance(userServiceRequest);

        //then
        UserServiceResponse response = userService.getUserBalance(1L);
        assertEquals(balance, response.getBalance());
    }

    @Test
    @DisplayName("충전 연속 두 번 요청시 성공 케이스")
    void test2() {
        // given
        Integer balance = 2000;
        UserServiceRequest userServiceRequest = new UserServiceRequest(1L, 1000);

        // when
        userService.chargeUserBalance(userServiceRequest);
        userService.chargeUserBalance(userServiceRequest);

        //then
        UserServiceResponse response = userService.getUserBalance(1L);
        assertEquals(balance, response.getBalance());
    }
}
