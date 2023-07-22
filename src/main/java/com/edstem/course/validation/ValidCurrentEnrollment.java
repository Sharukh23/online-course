package com.edstem.course.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrentEnrollmentValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrentEnrollment {
    String message() default "Current enrollment should be a positive number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
