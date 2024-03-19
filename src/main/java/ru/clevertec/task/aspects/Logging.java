package ru.clevertec.task.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import static java.time.format.DateTimeFormatter.ofPattern;

@Aspect
public class Logging {
    @Pointcut("execution(* *(..)) && @annotation(ru.clevertec.task.aspects.Log)")
    public void executeAfter() {
    }

    @AfterReturning(value = "executeAfter()", returning = "results")
    public void executeAfter(JoinPoint joinPoint, Object results) {
        String logMessage = getLogMessage(joinPoint, results);

        appendLogToFile(logMessage);
        System.out.println(joinPoint.getSignature().getName() + "-> " + joinPoint.getSignature().toShortString() + ": " + Arrays.toString(joinPoint.getArgs()) + "-> " + results);
    }

    private static String getLogMessage(JoinPoint joinPoint, Object results) {
        String name = joinPoint.getSignature().getName();
        String shortSignature = joinPoint.getSignature().toShortString();
        String args = Arrays.toString(joinPoint.getArgs());
        String dateTime = ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

        return String.format("%s, %s -> %s: %s -> %s\n", dateTime, name, shortSignature, args, results);
    }

    private static void appendLogToFile(String logMessage) {
        File file = new File("C:/Users/lika_piv/IdeaProjects/Clever-Bank/src/main/resources/logs/log.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
