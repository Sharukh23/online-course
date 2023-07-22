package com.edstem.course.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StudentIdValidator implements ConstraintValidator<ValidStudentId, Long> {

    @Override
    public boolean isValid(Long studentId, ConstraintValidatorContext constraintValidatorContext) {
        return studentId != null && studentId > 0;
    }
}
