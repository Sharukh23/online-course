package com.edstem.course.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CourseIdValidator implements ConstraintValidator<ValidCourseId, Long> {

    @Override
    public boolean isValid(Long courseId, ConstraintValidatorContext constraintValidatorContext) {
        return courseId != null && courseId > 0;
    }
}
