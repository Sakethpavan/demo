package com.example.demo.utility;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.Logger;

@Component
@Aspect
public class LoggingAspect {
    public static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.example.demo.service.*Service.*(..))", throwing = "exception")
    public void logServiceException(Exception exception) throws Exception {
        LOGGER.error(exception.getMessage(), exception);
    }

}