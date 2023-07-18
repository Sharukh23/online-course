package com.edstem.Course.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SameStudentIdException extends RuntimeException {
    public SameStudentIdException(Long id) {
        super("Student id " +id+" is already exists");
    }
}