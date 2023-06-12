package com.giuseppe.allureshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Pointcut("execution(* com.giuseppe.allureshop.services.FlowerService.*(..))")
    public void flowerServiceMethods() {}

    @Before("flowerServiceMethods()")
    public void beforeAdvice(JoinPoint joinPoint) {
        LOGGER.info("Before method: " + joinPoint.getSignature());
        LOGGER.info("Preparing for execution of " + joinPoint.getSignature().getName());
    }

    @After("flowerServiceMethods()")
    public void afterAdvice(JoinPoint joinPoint) {
        LOGGER.info("After method: " + joinPoint.getSignature());
        LOGGER.info("Done with execution of " + joinPoint.getSignature().getName());
    }
}

