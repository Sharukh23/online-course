package com.edstem.course.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CourseIdValidator.class)
@Documented
public @interface ValidCourseId {
    String message() default "Course Id should be a positive number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
