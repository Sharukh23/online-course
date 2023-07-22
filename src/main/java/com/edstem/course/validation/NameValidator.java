package com.edstem.course.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(name);
    }
}
