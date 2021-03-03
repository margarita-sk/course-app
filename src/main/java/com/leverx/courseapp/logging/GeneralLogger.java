package com.leverx.courseapp.logging;

import com.leverx.courseapp.user.dto.StudentDtoRegistration;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

@Aspect
@Configuration
@Log4j
public class GeneralLogger {

    @Pointcut("@annotation(com.leverx.courseapp.logging.annotations.Changeable)")
    public void changesInDbMethod() {
    }

    @Before("changesInDbMethod()")
    public void beforeCallChangesInDbMethod(JoinPoint joinPoint) {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(name + " is trying to make changes in database ");
        log.info("Method: " + joinPoint.getSignature());
    }


    @AfterReturning("changesInDbMethod()")
    public void afterSuccessInsertion(JoinPoint joinPoint) {
        log.info("Changes were successful ");
        var args = joinPoint.getArgs();
        Arrays.stream(args).forEach(o -> {
            if (o.getClass().equals(StudentDtoRegistration.class)) {
                var student = (StudentDtoRegistration) o;
                student.setPassword(null);
            }
            log.info(o);
        });
    }


    @AfterThrowing(value = "@annotation(com.leverx.courseapp.logging.annotations.Changeable)", throwing = "ex")
    public void afterThrowingException(RuntimeException ex) {
        log.error("Changes were failed: " + ex.getClass() + " : " + ex.getMessage());
    }

}
