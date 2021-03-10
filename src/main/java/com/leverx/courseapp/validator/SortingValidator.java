package com.leverx.courseapp.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class SortingValidator implements ConstraintValidator<Sorting, String> {
    @Override
    public boolean isValid(String sortBy, ConstraintValidatorContext constraintValidatorContext) {
        var sorts = List.of("id", "name", "description", "startAssignmentDate", "endAssignmentDate");
        return sorts.contains(sortBy);
    }
}
