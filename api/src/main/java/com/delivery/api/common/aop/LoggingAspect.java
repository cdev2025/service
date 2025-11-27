package com.delivery.api.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

   @Around("execution(* com.delivery.api..controller..*(..)) || " +
            "execution(* com.delivery.api..business..*(..)) || " +
            "execution(* com.delivery.api..service..*(..))"
    )
    //@Around("execution(* com.delivery.api..controller..*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable{

        String method = pjp.getSignature().toShortString();
        Object[] args = pjp.getArgs();

        log.info("▶️ [ENTER] {}", method);
        if(args != null && args.length>0) {
            log.info("      |- args = {}", args);
        }

        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long time = System.currentTimeMillis();

        log.info("◀️ [EXIT] {} ( {}ms )", method, time);
        log.info("      -- return = {}", result);

        return result;
    }
}
