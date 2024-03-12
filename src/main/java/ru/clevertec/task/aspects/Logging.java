package ru.clevertec.task.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class Logging {
    @Pointcut("execution(* *(..)) && @annotation(Log)")
    public void execute() {
    }

    @AfterReturning(value = "execute()", returning = "results")
    public void executeAfter(JoinPoint joinPoint, Object results) {
        System.out.println(joinPoint.getSignature().getName() + "-> " + joinPoint.getSignature().toShortString() + ": " + Arrays.toString(joinPoint.getArgs()) + "-> " + results);
    }
}
