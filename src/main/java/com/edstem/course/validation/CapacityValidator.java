package com.edstem.course.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CapacityValidator implements ConstraintValidator<ValidCapacity, Integer> {
    @Override
    public boolean isValid(
            Integer capacity, ConstraintValidatorContext constraintValidatorContext) {
        return capacity != null && capacity >= 0;
    }
}
