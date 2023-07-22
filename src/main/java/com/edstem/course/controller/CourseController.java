package com.edstem.course.controller;

import com.edstem.course.contract.Course;
import com.edstem.course.service.CourseService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/courses")
@RestController
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@Valid @RequestBody Course course) {
        Course courseDto = courseService.addCourse(course);
        return new ResponseEntity<>(courseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourseById(
            @PathVariable Long id, @Valid @RequestBody Course updatedCourse) {
        Course updatedCourses = courseService.updateCourseById(id, updatedCourse);
        return ResponseEntity.ok(updatedCourses);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable Long id) {
        String courseName = courseService.deleteCourseById(id);
        return ResponseEntity.ok("Course " + courseName + " with Id " + id + " has been deleted");
    }
}
