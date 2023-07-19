package com.edstem.Course.controller;

import com.edstem.Course.contract.Courses;
import com.edstem.Course.model.Course;
import com.edstem.Course.repository.CourseRepository;
import com.edstem.Course.service.CourseService;
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
    public ResponseEntity<List<Courses>> getAllCourses() {
        List<Courses> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Courses> getCourseById(@PathVariable Long id) {
        Courses course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<Courses> addCourses(@Valid @RequestBody Course courses) {
        Courses addedCourses = courseService.addCourses(courses);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCourses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Courses> updateCourseById(@PathVariable Long id, @Valid @RequestBody Course updatedCourse) {
        Courses updatedCourses = courseService.updateCourseById(id, updatedCourse);
        return ResponseEntity.ok(updatedCourses);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteCourseById(@PathVariable Long id) {
        courseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
