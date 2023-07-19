package com.edstem.Course.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnrollmentCapacityException extends RuntimeException {
    public EnrollmentCapacityException(int currentEnrollment, int capacity) {
        super("Current enrollment is " + currentEnrollment + " which exceeds course capacity of " + capacity);
    }
}