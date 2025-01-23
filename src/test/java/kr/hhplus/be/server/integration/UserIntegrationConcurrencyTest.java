package kr.hhplus.be.server.integration;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.User.service.UserService;
import kr.hhplus.be.server.domain.common.dto.UserServiceRequest;
import kr.hhplus.be.server.domain.common.dto.UserServiceResponse;
import kr.hhplus.be.server.domain.common.exception.ConcurrentOperationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Transactional
public class UserIntegrationConcurrencyTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("포인트 충전에 대한 동시성 제어 테스트: 성공")
    void test1() {
        // given
        UserServiceRequest userServiceRequest = new UserServiceRequest(1L, 500);
        long numberOfRequests = 2; // 동시 요청 개수
        CompletableFuture<Void> futures = new CompletableFuture<>();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // when
        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfRequests; i++) {
            futures = CompletableFuture.runAsync(() -> {
                try {
                    userService.chargeUserBalance(userServiceRequest);
                } catch (ConcurrentOperationException e) {
                    System.out.println("ConcurrentOperationException 발생: " + e.getMessage());
                }
                }, executorService);
            System.out.println("신청 횟수: " + (i+1));
        }

        CompletableFuture.allOf(futures).join();
        long end = System.currentTimeMillis();

        //then
        UserServiceResponse response = userService.getUserBalance(1L);
        long expectedBalance = 2000; // 두 개 요청이 성공해야 함
        System.out.println("최종 포인트" + response.getBalance());
        System.out.println("기대 포인트" + expectedBalance);

        if (response.getBalance() == expectedBalance) {
            System.out.println("비관적 락 테스트 성공");
        } else {
            System.out.println("비관적 락 테스트 실패: 충돌 처리 실패");
        }
        System.out.println("총 걸린 시간: " + (end - start) + "ms");
        executorService.shutdown();
    }


}
