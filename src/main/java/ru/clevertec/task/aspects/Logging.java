package ru.clevertec.task.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Logging {
    @Pointcut("execution(* *(..)) && @annotation(Log)")
    public void execute() {
    }

    @Before("execute()")
    public void executeBefore(JoinPoint joinPoint) {
        System.out.println("Signature: " + joinPoint.getSignature());
        System.out.println("Entered " + Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}
