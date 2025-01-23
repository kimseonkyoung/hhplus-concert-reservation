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
    @DisplayName("포인트 충전에 대한 동시성 제어 테스트: 실패")
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
        long expectedBalance = 1500; // 하나의 요청만 성공해야 함
        System.out.println("최종 포인트" + response.getBalance());
        System.out.println("기대 포인트" + expectedBalance);

        if (response.getBalance() == expectedBalance) {
            System.out.println("낙관적 락 테스트 성공");
        } else {
            System.out.println("낙관적 락 테스트 실패: 충돌 처리 실패");
        }
        System.out.println("총 걸린 시간: " + (end - start) + "ms");
        executorService.shutdown();
    }

    @Test
    @DisplayName("포인트 사용에 대한 동시성 제어 테스트: 실패 ")
    void test2() {
        // given
        long numberOfRequests = 10; // 동시 요청 개수
        CompletableFuture<Void> futures = new CompletableFuture<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // when
        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfRequests; i++) {
            futures = CompletableFuture.runAsync(() -> {
                userService.deductBalance(2L, 100);
            }, executorService);
            System.out.println("신청 횟수: " + i);
            System.out.println("남은 잔액: " + userService.getUserBalance(2L).getBalance());
        }

        CompletableFuture.allOf(futures).join();
        long end = System.currentTimeMillis();

        //then
        UserServiceResponse response = userService.getUserBalance(2L);
        long expectedBalance = 9000;
        System.out.println("최종 포인트" + response.getBalance());
        System.out.println("기대 포인트" + expectedBalance);

        if (Objects.equals(response.getBalance(), expectedBalance)) {
            System.out.println("동시성 제어 성공");
        } else {
            System.out.println("동시성 제어 실패: Race Condition 발생");
        }
        System.out.println("총 걸린 시간: " + (end - start) + "ms");
        executorService.shutdown();
    }

    @Test
    @DisplayName("포인트 충전 사용에 대한 동시성 테스트: 실패")
    void test3() {
        // given
        long numberOfRequests = 10; // 동시 요청 개수
        int initialPoints = 1000; // 유저 초기 셋팅 포인트
        UserServiceRequest userServiceRequest = new UserServiceRequest(3L, 1000);
        CompletableFuture<Void> futures = new CompletableFuture<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // when
        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfRequests; i++) {
            futures = CompletableFuture.runAsync(() -> {
                userService.chargeUserBalance(userServiceRequest);
                userService.deductBalance(3L, 1000);
            }, executorService);
            System.out.println("신청 횟수: " + i);
        }

        CompletableFuture.allOf(futures).join();
        long end = System.currentTimeMillis();

        //then
        UserServiceResponse response = userService.getUserBalance(3L);
        System.out.println("최종 포인트" + initialPoints);
        System.out.println("기대 포인트" + response.getBalance());

        if (Objects.equals(response.getBalance(), initialPoints)) {
            System.out.println("동시성 제어 성공");
        } else {
            System.out.println("동시성 제어 실패: Race Condition 발생");
        }
        System.out.println("총 걸린 시간: " + (end - start) + "ms");
        executorService.shutdown();

    }
}
