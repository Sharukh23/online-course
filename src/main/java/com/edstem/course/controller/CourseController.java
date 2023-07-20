package com.edstem.course.controller;

import com.edstem.course.contract.CourseDto;
import com.edstem.course.model.Course;
import com.edstem.course.repository.CourseRepository;
import com.edstem.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/courses")
@RestController
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }


    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        CourseDto course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<CourseDto> addCourses(@Valid @RequestBody Course courses) {
        CourseDto addedCourses = courseService.addCourses(courses);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCourses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourseById(@PathVariable Long id, @Valid @RequestBody Course updatedCourse) {
        CourseDto updatedCourses = courseService.updateCourseById(id, updatedCourse);
        return ResponseEntity.ok(updatedCourses);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return ResponseEntity.ok("Course with Id " + id + " has been deleted");
    }
}
