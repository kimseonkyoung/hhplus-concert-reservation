package kr.hhplus.be.server.common.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AllRequiredLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(AllRequiredLogAspect.class);

    // 비즈니스 메소드
    @Pointcut("@within(kr.hhplus.be.server.common.log.AllRequiredLogger) || @annotation(kr.hhplus.be.server.common.log.AllRequiredLogger)")
    public void AllRequiredLoggerAspect() {}

    @Around("AllRequiredLoggerAspect()")
    public Object logModule(ProceedingJoinPoint joinPoint) throws  Throwable {
        long startTime = System.currentTimeMillis();
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());

        try {
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("Exiting method: {} with result: {} Execution time: {} ms", joinPoint.getSignature(), result, elapsedTime);
            return result;
        } catch (Exception e) {
            logger.error("Exception occurred in method: {}", joinPoint.getSignature(), e);
            throw e;
        }
    }
}
