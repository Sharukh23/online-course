package com.edstem.course.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CurrentEnrollmentValidator
        implements ConstraintValidator<ValidCurrentEnrollment, Integer> {
    @Override
    public boolean isValid(
            Integer currentEnrollment, ConstraintValidatorContext constraintValidatorContext) {
        return currentEnrollment != null && currentEnrollment >= 0;
    }
}
