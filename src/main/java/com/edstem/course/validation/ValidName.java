package com.edstem.course.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "Name should not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
