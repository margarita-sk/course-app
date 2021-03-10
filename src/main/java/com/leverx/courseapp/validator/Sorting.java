package com.leverx.courseapp.validator;

import com.nimbusds.jose.Payload;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = SortingValidator.class)
@Documented
public @interface Sorting {

    String message() default "{Sorting.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}