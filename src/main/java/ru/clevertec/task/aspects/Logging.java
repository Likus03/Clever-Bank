package ru.clevertec.task.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class Logging {
    @Pointcut("execution(void *(..)) && @annotation(ru.clevertec.task.aspects.Log)")
    public void executeBefore() {
    }

    @Before(value = "executeBefore()")
    public void executeBefore(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + "-> " + joinPoint.getSignature().toShortString() + ": " + Arrays.toString(joinPoint.getArgs()));
    }

    @Pointcut("execution(* *(..)) && !execution(void *(..)) && @annotation(ru.clevertec.task.aspects.Log)")
    public void executeAfter() {
    }

    @AfterReturning(value = "executeAfter()", returning = "results")
    public void executeAfter(JoinPoint joinPoint, Object results) {
        System.out.println(joinPoint.getSignature().getName() + "-> " + joinPoint.getSignature().toShortString() + ": " + Arrays.toString(joinPoint.getArgs()) + "-> " + results);
    }
}
