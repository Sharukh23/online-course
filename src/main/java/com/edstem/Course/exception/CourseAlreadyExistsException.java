package com.edstem.Course.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseAlreadyExistsException extends RuntimeException {
    public CourseAlreadyExistsException(String courseName) {
        super("Course " +courseName+" is already exists");
    }
}