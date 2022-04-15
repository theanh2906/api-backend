package com.backend.api.aspects;

import com.backend.api.services.FshareService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class FshareServiceAspect {
    private final Logger logger = LoggerFactory.getLogger(FshareServiceAspect.class);
    @Autowired
    private HttpServletRequest request;
    @Before("execution(* com.backend.api.services.FshareService.*(..)) && target(fshareService)")
    public void aroundExecution(JoinPoint joinPoint, FshareService fshareService) {
        fshareService.setFshareData(request);
    }
}
