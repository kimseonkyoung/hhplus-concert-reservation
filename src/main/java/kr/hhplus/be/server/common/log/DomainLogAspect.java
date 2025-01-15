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
public class DomainLogAspect {

    private static final Logger domainLogger = LoggerFactory.getLogger(DomainLogAspect.class);

    @Pointcut("@annotation(kr.hhplus.be.server.common.log.DomainLogger)")
    public void domainLogPointcut() {}

    @Around("domainLogPointcut()")
    public Object logDomainOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        domainLogger.info("Entering domain method: {}", joinPoint.getSignature());
        try {
            Object result = joinPoint.proceed();
            domainLogger.info("Exiting domain method: {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            domainLogger.error("Exception in domain method: {}", joinPoint.getSignature(), e);
            throw e;
        }
    }
}
