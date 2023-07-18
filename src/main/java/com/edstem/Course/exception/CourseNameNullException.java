package com.edstem.Course.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNameNullException extends RuntimeException {
    public CourseNameNullException() {
        super("Course name should not be null");
    }
}