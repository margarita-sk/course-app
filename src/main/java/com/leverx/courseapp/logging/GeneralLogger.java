package com.leverx.courseapp.logging;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.user.dto.StudentDtoRegistration;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Configuration
public class GeneralLogger {

    private final static Logger LOG = Logger.getLogger(GeneralLogger.class);

    @Pointcut("@annotation(com.leverx.courseapp.logging.annotations.DbChangeable)")
    public void changesInDbMethod() {
    }

    @Before("changesInDbMethod()")
    public void beforeCallChangesInDbMethod(JoinPoint joinPoint) {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        LOG.info(name + " is trying to make changes in database ");
        LOG.info("Method: " + joinPoint.getSignature());
    }


    @AfterReturning("changesInDbMethod()")
    public void afterSuccessInsertion(JoinPoint joinPoint) {
        LOG.info("Changes were successful ");
        var args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i].getClass().equals(StudentDtoRegistration.class)) {
                var student = (StudentDtoRegistration) args[i];
                student.setPassword(null);
            }
            LOG.info(args[i]);
        }
    }


    @AfterThrowing(value = "@annotation(com.leverx.courseapp.logging.annotations.DbChangeable)", throwing = "ex")
    public void afterThrowingException(RuntimeException ex) {
        LOG.error("Changes were failed: " + ex.getClass() + " : " + ex.getMessage());
    }

}
