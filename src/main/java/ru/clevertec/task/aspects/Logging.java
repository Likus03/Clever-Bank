package ru.clevertec.task.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = LocalDateTime.now().format(formatter);

        return String.format("%s, %s -> %s: %s -> %s\n", dateTime, name, shortSignature, args, results);
    }

    private static void appendLogToFile(String logMessage) {
        Path path = Paths.get("../webapps/ROOT/WEB-INF/classes/logs/log.txt");
        File file = new File(path.toUri());
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
