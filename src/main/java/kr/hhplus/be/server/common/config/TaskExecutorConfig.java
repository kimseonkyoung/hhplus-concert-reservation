package kr.hhplus.be.server.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class TaskExecutorConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10); // 최대 10개 스레드까지 확장
        executor.setQueueCapacity(500); // 대기열 500개
        executor.setThreadNamePrefix("KafkaSender-"); // 스레드 이름 설정

        // Graceful Shutdown 설정
        executor.setWaitForTasksToCompleteOnShutdown(true); // 종료 시 대기
        executor.setAwaitTerminationSeconds(10); // 최대 10초까지 종료 대기

        executor.initialize();
        return executor;
    }
}
