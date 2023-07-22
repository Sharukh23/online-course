package com.edstem.course.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SameStudentIdException extends RuntimeException {
    public SameStudentIdException(Long studentId, String courseName) {
        super("Student id " + studentId + " is already registered with course " + courseName);
    }
}
