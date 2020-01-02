package org.hyl.log.aop;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void controller() {
    }

    @Before("controller()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("【开始请求】: {}.{}()", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("【请求参数】: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @Around("controller()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object object = null;
        try {
            object = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            stopWatch.stop();
        }
        log.info("【结束请求】: {}.{}()", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
        log.info("【请求耗时】: {} 毫秒", stopWatch.getTime());
        return object;
    }
}
