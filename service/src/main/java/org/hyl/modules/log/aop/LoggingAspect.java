package org.hyl.modules.log.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Slf4j
@Aspect
public class LoggingAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void controller() {
    }

    @Before("controller()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("【开始请求】: {}.{}()", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("【请求参数】: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @Around("controller()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object object;
        try {
            object = proceedingJoinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info("【结束请求】: {}.{}()", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
            log.info("【请求耗时】: {} 毫秒", stopWatch.getTime());
        }
        return object;
    }
}
