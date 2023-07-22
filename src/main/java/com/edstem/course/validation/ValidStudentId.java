package com.edstem.course.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentIdValidator.class)
@Documented
public @interface ValidStudentId {
    String message() default "Student Id should be positive number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
