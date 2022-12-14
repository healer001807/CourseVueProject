package com.auggie.student_server.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: CourseVueProject
 * @description: 切面日志
 * @author: kangwei
 * @create: 2022-12-14 19:06
 **/
@Component
@Aspect
public class LogAspect {

    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * 切入点
     */
    @Pointcut("execution(* com.auggie.student_server.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void logBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String declaringTypeName = signature.getDeclaringTypeName();
        declaringTypeName = declaringTypeName.substring(declaringTypeName.lastIndexOf(".") + 1);
        String name = signature.getName();
        LOGGER.info("日志开始记录==>" + declaringTypeName + "==>" + name);
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void logAfter(Object result) {
        LOGGER.info("日志结束记录==>" + result);
    }
}
